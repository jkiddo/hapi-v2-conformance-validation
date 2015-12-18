// $codepro.audit.disable logExceptions
package net.ihe.gazelle.hl7.validator.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.ihe.gazelle.hl7.validator.core.GazelleHL7Assertion.AssertionType;
import net.ihe.gazelle.hl7.validator.report.ValidationResults;
import net.ihe.gazelle.hl7.validator.rules.IHEValidationRule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.conf.ProfileException;
import ca.uhn.hl7v2.conf.check.Validator;
import ca.uhn.hl7v2.conf.spec.message.AbstractComponent;
import ca.uhn.hl7v2.conf.spec.message.AbstractSegmentContainer;
import ca.uhn.hl7v2.conf.spec.message.Component;
import ca.uhn.hl7v2.conf.spec.message.Field;
import ca.uhn.hl7v2.conf.spec.message.ProfileStructure;
import ca.uhn.hl7v2.conf.spec.message.Seg;
import ca.uhn.hl7v2.conf.spec.message.SegGroup;
import ca.uhn.hl7v2.conf.spec.message.StaticDef;
import ca.uhn.hl7v2.conf.spec.message.SubComponent;
import ca.uhn.hl7v2.model.Composite;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Primitive;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.Structure;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.model.primitive.TSComponentOne;
import ca.uhn.hl7v2.parser.EncodingCharacters;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.util.Terser;
import ca.uhn.hl7v2.validation.PrimitiveTypeRule;
import ca.uhn.hl7v2.validation.ValidationException;
import ca.uhn.hl7v2.validation.impl.DefaultValidation;
import ca.uhn.hl7v2.validation.impl.ValidationContextImpl;

/**
 * Conformance validator defined for Gazelle platform.
 * 
 * @author Anne-Gaëlle Bergé > anne-gaelle@ihe-europe.net
 */
public class GazelleValidator {

	private static final String MSH_21 = "/MSH-21";
	// used to check for content in parts of a message
	private static Logger log = LoggerFactory.getLogger(GazelleValidator.class);
	private static final IHEValidationContext VALIDATION_CONTEXT = IHEValidationContext.SINGLETON;
	private static final String EVENT_TYPE_LOCATION = "/MSH-9-2";
	private static final String MESSAGE_STRUCTURE_LOCATION = "/MSH-9-3";
	private static final String MESSAGE_TYPE_LOCATION = "/MSH-9-1";
	private static final String MESSAGE_VERSION_LOCATION = "/MSH-12-1";

	private EncodingCharacters enc;
	private ResourceStoreFactory resourceStoreFactory;
	private String hl7Version;
	private String profileOid;
	private Message message;
	private PathBuilder hl7Path;
	private String currentSegmentName;
	private int currentFieldNumber;
	private ValidationResults results;
	private String messageProfileIdentifier;

	/**
	 * creates a new instance of MyDefaultValidator
	 * @param resourceStoreFactory
	 * @param results
	 */
	public GazelleValidator(ResourceStoreFactory resourceStoreFactory, ValidationResults results) {
		// the | is assumed later -- don't change
		enc = new EncodingCharacters('|', null);
		this.resourceStoreFactory = resourceStoreFactory;
		this.results = results;
	}

	/**
	 * @param hl7Version
	 * 
	 * @see Validator#validate
	 */
	public void validate(Message inMessage, StaticDef profile, String hl7Version) {
		this.hl7Version = hl7Version;
		Terser t = new Terser(inMessage);
		this.profileOid = profile.getIdentifier();
		this.message = inMessage;

		// check message type
		String msgType = null;
		try {
			msgType = t.get(MESSAGE_TYPE_LOCATION);
		} catch (HL7Exception e1) {
			results.addNotification("Unable to extract the message type from the message header",
					GazelleErrorCode.REQUIRED_FIELD_MISSING, null, null);
			((GazelleHL7Exception) results.getLastNotification()).setHl7Path(MESSAGE_TYPE_LOCATION);
		}
		if ((msgType == null) || !msgType.equals(profile.getMsgType())) {
			results.addNotification("Message type declared in the message (" + msgType
					+ ") does not match the one expected by the profile (" + profile.getMsgType() + ")",
					GazelleErrorCode.UNSUPPORTED_MESSAGE_TYPE, null, null);
			((GazelleHL7Exception) results.getLastNotification()).setHl7Path(MESSAGE_TYPE_LOCATION);
		}
		// check trigger event
		String evType = null;
		try {
			evType = t.get(EVENT_TYPE_LOCATION);
		} catch (HL7Exception e1) {
			results.addNotification("Unable to extract the trigger event from the message header",
					GazelleErrorCode.REQUIRED_FIELD_MISSING, null, null);
			((GazelleHL7Exception) results.getLastNotification()).setHl7Path(EVENT_TYPE_LOCATION);
		}
		if (evType != null) {
			// not checked if profile event type is ALL (used when a profile
			// describes the ACK for several trigger events)
			if (!evType.equals(profile.getEventType()) && !profile.getEventType().equalsIgnoreCase("ALL")) {
				results.addNotification("Event type declared in the message (" + evType
						+ ") does not match the one expected by the profile (" + profile.getEventType() + ")",
						GazelleErrorCode.UNSUPPORTED_EVENT_CODE, null, null);
				((GazelleHL7Exception) results.getLastNotification()).setHl7Path(EVENT_TYPE_LOCATION);
			}
		} else {
			results.addNotification("Event type is empty and should not", GazelleErrorCode.APPLICATION_INTERNAL_ERROR,
					null, null);
			((GazelleHL7Exception) results.getLastNotification()).setHl7Path(EVENT_TYPE_LOCATION);
		}

		// check message structure
		String msgStruct = null;
		try {
			msgStruct = t.get(MESSAGE_STRUCTURE_LOCATION);
		} catch (HL7Exception e1) {
			results.addNotification("Unable to extract the message structure from the message header", null, null);
			((GazelleHL7Exception) results.getLastNotification()).setHl7Path(MESSAGE_STRUCTURE_LOCATION);
		}
		// as MSH-9-3 is optional in HL7v2.3.1, we cannot raise an error if this component is missing, only check there that if the
		// MSH-9-3 is provided, it matches the one declared in the message profile
		if ((msgStruct != null) && !msgStruct.equals(profile.getMsgStructID())) {
			results.addNotification("Message structure declared in the message (" + msgStruct
					+ ") does not match the one expected by the message profile (" + profile.getMsgStructID() + ")",
					null, null);
			((GazelleHL7Exception) results.getLastNotification()).setHl7Path(MESSAGE_STRUCTURE_LOCATION);
		} else if (msgStruct == null && !inMessage.getClass().getSimpleName().equals(profile.getMsgStructID())) {
			results.addNotification("MSH-9-3 is empty, hapi uses " + inMessage.getClass().getSimpleName()
					+ " which does not match the structure declared in the profile (" + profile.getMsgStructID()
					+ "), the outcome of the validation might not be accurate.", null, null);
			((GazelleHL7Exception) results.getLastNotification()).setHl7Path(MESSAGE_STRUCTURE_LOCATION);
		}

		// check message version
		String msgVersion = null;
		try {
			msgVersion = t.get(MESSAGE_VERSION_LOCATION);
		} catch (HL7Exception e1) {
			results.addNotification("Unable to extract the message structure from the message header", null, null);
			((GazelleHL7Exception) results.getLastNotification()).setHl7Path("/MSH");
		}
		if ((msgVersion == null) || !msgVersion.equals(hl7Version)) {
			results.addNotification("Message version declared in the message (" + msgVersion
					+ ") does not match the one expected by the message profile (" + hl7Version + ")", null, null);
			((GazelleHL7Exception) results.getLastNotification()).setHl7Path(MESSAGE_VERSION_LOCATION);
		}

		try {
			// store message profile identifiers for future use
			messageProfileIdentifier = t.get(MSH_21);
		}catch (HL7Exception e){
			messageProfileIdentifier = null;
		}
		// check message groups
		hl7Path = new PathBuilder();
		hl7Path.add(inMessage.getName());
		testGroup(inMessage, profile, profile.getIdentifier());
	}

	/**
	 * Tests a group against a group section of a profile.
	 */
	public void testGroup(Group msgGroup, AbstractSegmentContainer profile, String profileID) {
		List<String> allowedStructures = new ArrayList<String>();
		List<String> forbiddenStructures = new ArrayList<String>();

		for (int childIndex = 1; childIndex <= profile.getChildren(); childIndex++) {
			ProfileStructure profileStructure = profile.getChild(childIndex);
			hl7Path.add(profileStructure.getName());
			// only test a structure in detail if it isn't X
			if (!profileStructure.getUsage().equalsIgnoreCase("X")) {
				allowedStructures.add(profileStructure.getName());

				// see which instances have content
				Structure[] messageStructures = null;

				try {
					messageStructures = msgGroup.getAll(profileStructure.getName());
				}
				// exception thrown if the named Structure is not part of this
				// Group.
				catch (HL7Exception e) {
					results.addProfileException(
							profileStructure.getName()
									+ " is expected by the message profile but does not appear in the java class representing the message structure",
							hl7Path, null);
					continue;
				}
				if (messageStructures != null) {
					testCardinality(messageStructures.length, profileStructure.getMin(), profileStructure.getMax(),
							profileStructure.getUsage(), profileStructure.getName(),
							GazelleErrorCode.SEGMENT_SEQUENCE_ERROR);

					// test children on instances with content
					int count = 0;
					for (Structure structure : messageStructures) {
						hl7Path.addRep(count);
						testStructure(structure, profileStructure, profileID);
						count++;
					}
				}
			} else {
				forbiddenStructures.add(profileStructure.getName());
			}
			hl7Path.removeLastItem();
		}
		// complain about X/unspecified structures that have content
		checkForExtraStructures(msgGroup, allowedStructures, forbiddenStructures);
	}

	/**
	 * Checks a group's children against a list of allowed structures for the group (ie those mentioned in the message profile with usage other than X). Returns a list of exceptions representing
	 * structures that appear in the message but are not supposed to.
	 * 
	 * @param group
	 *            (the group to be tested)
	 * @param allowedStructures
	 *            (the structures which appears in the message profile as not X)
	 * @param forbiddenStructures
	 *            (marked as X in the message profile)
	 */
	private void checkForExtraStructures(Group group, List<String> allowedStructures, List<String> forbiddenStructures) {
		String[] childNames = group.getNames();
		for (String childName : childNames) {

			if (forbiddenStructures.contains(childName)) {
				try {
					Structure[] structures = group.getAll(childName);
					if (structures.length > 0) {
						for (Structure structure : structures) {
							results.addNotification(
									childName
											+ " appears in the message but is specified as forbidden (X) in the message profile",
									GazelleErrorCode.NOT_ALLOWED, hl7Path, encodeStructure(structure));
							((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setSegmentName(
									group.getName());
						}
					} else {
						results.addAssertion(childName + " does not appear in the message", hl7Path,
								AssertionType.FORBIDDEN_ELEMENT);
					}
				} catch (HL7Exception he) {
					// this exception should never occur since we are iterating
					// on the name of the children of this group
					log.error(childName + " does not exist in " + group.getName());
				}
			} else if (!allowedStructures.contains(childName)) {
				try {
					Structure[] structures = group.getAll(childName);
					if (structures.length > 0) {
						for (Structure structure : structures) {
							results.addNotification(childName
									+ " appears in the message but is not defined in the message profile",
									GazelleErrorCode.NOT_ALLOWED, hl7Path, encodeStructure(structure));
							((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setSegmentName(
									group.getName());
						}
					}
				} catch (HL7Exception he) {
					// this exception should never occur since we are iterating
					// on the name of the children of this group
					log.error(childName + " does not exist in " + group.getName());
				}
			}
		}
	}

	/**
	 * Checks cardinality and creates an appropriate exception if out of bounds. The usage code is needed because if min cardinality is > 0, the min # of reps is only required if the usage code is 'R'
	 * (see HL7 v2.5 section 2.12.6.4).
	 * 
	 * @param reps
	 *            the number of reps
	 * @param min
	 *            the minimum number of reps
	 * @param max
	 *            the maximum number of reps (-1 means *)
	 * @param usage
	 *            the usage code
	 * @param name
	 *            the name of the repeating structure (used in exception msg)
	 * @param errorTypeIfMissing
	 * 
	 * @return null if cardinality OK, exception otherwise
	 */
	protected void testCardinality(int reps, int min, int max, String usage, String name,
			GazelleErrorCode errorTypeIfMissing) {
		Optionality optionality = Optionality.getEnum(usage);
		switch (optionality) {
		case REQUIRED:
			if (reps == 0) {
				results.addNotification("Element '" + name
						+ "' is specified as required (R) but not present in the message", errorTypeIfMissing, hl7Path,
						null);
				return;
			} else if ((reps > 0) && (reps < min)) {
				results.addNotification(name + " must have at least " + min + " repetitions (has " + reps + ")",
						GazelleErrorCode.CARDINALITY_ERROR, hl7Path, null);
				return;
			} else {
				results.addAssertion(name + " shall be present", hl7Path, AssertionType.REQUIRED_ELEMENT);
			}
			break;
		case CONDITIONAL:
			// check message rules
			List<IHEValidationRule> rules = VALIDATION_CONTEXT.getIHEValidationRules(profileOid,
					messageProfileIdentifier, hl7Path.toString());
			if (!rules.isEmpty()) {
				for (IHEValidationRule rule : rules) {
					ValidationException[] validationExceptions = rule.test(message, hl7Path.toString());
					if (validationExceptions != null) {
						for (ValidationException ve : validationExceptions) {
							results.addNotification(ve.getMessage() + (" (see " + rule.getSectionReference() + ")"),
									rule.getSeverity(), hl7Path, null);
						}
						return;
					}
				}
			} else {
				results.addNotification(
						"The optionality of this element is set as 'conditional' and no rule has been defined, "
								+ "refer to the specification to check the optionality which applies in the context of this message",
						GazelleErrorCode.CONDITIONAL, hl7Path, null);
			}
			break;
		case NOT_SUPPORTED:
			if (reps > 0) {
				results.addNotification("Element '" + name
						+ "' is present in the message but specified as not used (X) by the message profile",
						GazelleErrorCode.USAGE, hl7Path, null);
				return;
			}
			break;
		case OPTIONAL:
			if (reps > 0) {
				results.addAssertion(name + " can be present", hl7Path, AssertionType.OPTIONAL_ELEMENT);
				return;
			}
			break;
		case REQUIRED_OR_EMPTY:
			break;
		default:
			break;
		}

		if (!optionality.equals(Optionality.REQUIRED) && (reps > 0) && (reps < min)) {
			results.addNotification("If present, " + name + " must have at least " + min + " repetitions (has " + reps
					+ ")", GazelleErrorCode.CARDINALITY_ERROR, hl7Path, null);
			return;
		}
		if ((max > 0) && (reps > max)) {
			results.addNotification(name + " must have no more than " + max + " repetitions (has " + reps + ")",
					GazelleErrorCode.CARDINALITY_ERROR, hl7Path, null);
			return;
		}
		if ((reps > 0) && (reps >= min)) {
			results.addAssertion(name + " is repeated at least " + min + " time(s)", hl7Path, AssertionType.CARDINALITY);
		}
		if ((max > 0) && (reps > 0) && (reps <= max)) {
			results.addAssertion(name + " is repeated at most " + max + " time(s)", hl7Path, AssertionType.CARDINALITY);
		}
	}

	/**
	 * Tests a structure (segment or group) against the corresponding part of a profile.
	 */
	public void testStructure(Structure msgStructure, ProfileStructure profileStructure, String profileID) {
		if (profileStructure instanceof Seg) {
			if (Segment.class.isAssignableFrom(msgStructure.getClass())) {
				testSegment((Segment) msgStructure, (Seg) profileStructure, profileID);
			} else {
				results.addProfileException("Mismatch between a segment in the message profile and the structure "
						+ msgStructure.getClass().getName() + " in the message", hl7Path, null);
			}
		} else if (profileStructure instanceof SegGroup) {
			if (Group.class.isAssignableFrom(msgStructure.getClass())) {
				testGroup((Group) msgStructure, (SegGroup) profileStructure, profileID);
			} else {
				results.addProfileException("Mismatch between a group in the message profile and the structure "
						+ msgStructure.getClass().getName() + " in the message", hl7Path, null);
			}
		}
	}

	/**
	 * Tests a segment against a segment section of a message profile.
	 */
	public void testSegment(ca.uhn.hl7v2.model.Segment msgSegment, Seg profileSegment, String profileID) {
		List<Integer> allowedFields = new ArrayList<Integer>();
		List<Integer> forbiddenFields = new ArrayList<Integer>();

		currentSegmentName = msgSegment.getName();

		for (int fieldIndex = 1; fieldIndex <= profileSegment.getFields(); fieldIndex++) {
			Field profileField = profileSegment.getField(fieldIndex);
			hl7Path.add(msgSegment.getName() + "-" + fieldIndex + "(" + profileField.getName() + ")");
			currentFieldNumber = fieldIndex;
			// only test a field in detail if it isn't X
			if (!profileField.getUsage().equalsIgnoreCase(Optionality.NOT_SUPPORTED.getKey())) {
				allowedFields.add(fieldIndex);

				// see which instances have content
				Type[] messageFields = null;
				try {
					messageFields = msgSegment.getField(fieldIndex);
				} catch (HL7Exception e) {
					results.addProfileException(
							"Field " + fieldIndex + " is not defined for segment " + msgSegment.getName()
									+ "in the Java class representing this segment but appears in the message profile",
							hl7Path, null);
					continue;
				}
				if (messageFields != null) {
					// check cardinality matches profile requirements
					testCardinality(messageFields.length, profileField.getMin(), profileField.getMax(),
							profileField.getUsage(), profileField.getName(), GazelleErrorCode.REQUIRED_FIELD_MISSING);
					// test field instances with content
					int count = 0;
					for (Type messageField : messageFields) {
						try {
							if (!messageField.encode().isEmpty()) {
								hl7Path.addRep(count);
								// escape field value when checking length
								testField(messageField, profileField, fieldIndex,
										!(currentSegmentName.equalsIgnoreCase("MSH") && (fieldIndex < 3)), profileID);
							}
						} catch (HL7Exception e) {
							log.debug(e.getMessage(), e);
						} catch (NullPointerException e){
							log.info(e.getMessage(), e);
							results.addProfileException(
									"Unable to perform checks for field at " + hl7Path.toString(),
									hl7Path, null);
						}
						count++;
					}
				}
			} else {
				forbiddenFields.add(fieldIndex);
			}
			hl7Path.removeLastItem();
		}

		// complain about X fields with content
		checkForExtraFields(msgSegment, allowedFields, forbiddenFields);
	}

	/**
	 * Checks a segment against a list of allowed fields (ie those mentioned in the profile with usage other than X). Returns a list of exceptions representing field that appear but are not supposed
	 * to.
	 * 
	 * @param allowedFields
	 *            an array of Integers containing field #s of allowed fields
	 * @param forbiddenFields
	 * 
	 */
	private void checkForExtraFields(Segment segment, List<Integer> allowedFields, List<Integer> forbiddenFields) {
		for (int i = 1; i <= segment.numFields(); i++) {
			if (forbiddenFields.contains(i)) {
				try {
					Type[] reps = segment.getField(i);
					for (Type type : reps) {
						if (hasContent(type)) {
							results.addNotification(
									"Field '"
											+ segment.getName()
											+ "-"
											+ i
											+ "' appears in the message but is specified as forbidden (X) in the message profile",
									GazelleErrorCode.NOT_ALLOWED, hl7Path, PipeParser.encode(type, enc));
							((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setSegmentName(
									segment.getName());
							((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setFieldPosition(i);
						} else {
							results.addAssertion("Field '" + segment.getName() + "-" + i + "' is not present", hl7Path,
									AssertionType.FORBIDDEN_ELEMENT);
						}
					}
				} catch (HL7Exception he) {
					// this exception should never occur since we are iterating
					// on the number of fields in the segment
					log.error("Index out of bound: field" + segment.getName() + "-" + i + " does not exist");
				}
			} else if (!allowedFields.contains(i)) {
				try {
					Type[] reps = segment.getField(i);
					for (Type type : reps) {
						if (hasContent(type)) {
							results.addNotification("Field '" + segment.getName() + "-" + i
									+ "' appears in the message but is not defined in the message profile",
									GazelleErrorCode.NOT_ALLOWED, hl7Path, PipeParser.encode(type, enc));
							((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setSegmentName(
									segment.getName());
							((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setFieldPosition(i);
						}
					}
				} catch (HL7Exception he) {
					// this exception should never occur since we are iterating
					// on the number of fields in the segment
					log.error("Index out of bound: field" + segment.getName() + "-" + i + " does not exist");
				}
			}
		}
	}

	/**
	 * Tests a Type against the corresponding section of a profile.
	 * 
	 * @param value
	 *            optional encoded form of type (if you want to specify this -- if null, default pipe-encoded form is used to check length and constant val)
	 * @param testUsage
	 * 
	 */
	public void testType(Type msgType, AbstractComponent<?> profileType, String encoded, String profileID,
			boolean testUsage) {
		String value = encoded;
		if (value == null) {
			value = PipeParser.encode(msgType, this.enc);
		}

		if (testUsage) {
			testUsage(value, profileType.getUsage(), profileType.getName());
		}

		if (!profileType.getUsage().equals(Optionality.NOT_SUPPORTED.getKey())) {
			// check datatype
			String typeClass = msgType.getClass().getName();
			// TS-1 is of type TSComponentOne by hapi but standard (and
			// consequently message profile) defines ST
			if (((msgType instanceof ca.uhn.hl7v2.model.v231.datatype.TSComponentOne) || (msgType instanceof ca.uhn.hl7v2.model.v24.datatype.TSComponentOne))
					&& !profileType.getDatatype().equals("ST")) {
				results.addNotification("Datatype ST does not match the one declared in the message profile ("
						+ profileType.getDatatype() + ")", GazelleErrorCode.DATA_TYPE_ERROR, hl7Path, value);
			} else if (!(msgType instanceof TSComponentOne) && (typeClass.indexOf("." + profileType.getDatatype()) < 0)) {
				typeClass = typeClass.substring(typeClass.lastIndexOf('.') + 1);
				if (!typeClass.equals("Varies") && !typeClass.equals("QIP")) {
					results.addNotification("Datatype " + typeClass
							+ " does not match the one declared in the message profile (" + profileType.getDatatype()
							+ ")", GazelleErrorCode.DATA_TYPE_ERROR, hl7Path, value);
				}
			} else {
				results.addAssertion("Datatype " + profileType.getDatatype() + " is used", hl7Path,
						AssertionType.DATATYPE);
			}

			// check length
			if (value.length() > profileType.getLength()) {
				results.addNotification(
						"Element '" + profileType.getName() + "' has length " + value.length()
								+ " which exceeds the maximum length defined in the message profile ("
								+ profileType.getLength() + ")", GazelleErrorCode.LENGTH_ERROR, hl7Path, value);
				((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setSegmentName(
						currentSegmentName);
				((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setFieldPosition(
						currentFieldNumber);
			} else {
				results.addAssertion(
						"Length of the element does not exceed the length defined in the message profile ("
								+ profileType.getLength() + ")", hl7Path, AssertionType.LENGTH);
			}

			// check constant value
			if ((value.length() > 0) && (profileType.getConstantValue() != null)
					&& (profileType.getConstantValue().length() > 0)) {
				if (!value.equals(profileType.getConstantValue())) {
					results.addNotification(
							"'" + value + "' is not equal to the constant value '" + profileType.getConstantValue()
									+ "' defined in the message profile", GazelleErrorCode.WRONG_CONSTANT_VALUE,
							hl7Path, value);
					((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setSegmentName(
							currentSegmentName);
					((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setFieldPosition(
							currentFieldNumber);
				} else {
					results.addAssertion(
							"The constant value defined in the message profile (" + profileType.getConstantValue()
									+ ") is used", hl7Path, AssertionType.VALUE);
				}
			}

			// if profileType is Primitive, test it
			if (Primitive.class.isAssignableFrom(msgType.getClass())) {
				testPrimitive((Primitive) msgType, profileType);
			}

			// check value defined by tables
			testTypeAgainstTable(msgType, profileType, profileID);
		}
	}

	/**
	 * Tests an element against the corresponding usage code. The element is required in its encoded form.
	 * 
	 * @param encoded
	 *            the pipe-encoded message element
	 * @param usage
	 *            the usage code (e.g. "RE")
	 * @param name
	 *            the name of the element (for use in exception messages)
	 * @returns null if there is no problem, an GazelleHL7Exception otherwise
	 */
	private void testUsage(String encoded, String usage, String name) {
		Optionality optionality = Optionality.getEnum(usage);
		switch (optionality) {
		case REQUIRED:
			if (encoded.isEmpty()) {
				results.addNotification("Required element '" + name + "' is missing", GazelleErrorCode.USAGE, hl7Path,
						null);
				((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setSegmentName(
						currentSegmentName);
				((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setFieldPosition(
						currentFieldNumber);
				return;
			} else {
				results.addAssertion(name + " shall be present", hl7Path, AssertionType.REQUIRED_ELEMENT);
			}
			break;
		case CONDITIONAL:
			List<IHEValidationRule> rules = VALIDATION_CONTEXT.getIHEValidationRules(profileOid,
					messageProfileIdentifier, hl7Path.toString());
			if (!rules.isEmpty()) {
				for (IHEValidationRule rule : rules) {
					ValidationException[] validationExceptions = rule.test(message, hl7Path.toString());
					if (validationExceptions != null) {
						for (ValidationException ve : validationExceptions) {
							results.addNotification(ve.getMessage() + (" (see " + rule.getSectionReference() + ")"),
									rule.getSeverity(), hl7Path, null);
						}
						return;
					}
					else {
						results.addAssertion("Conditional usage OK", hl7Path, AssertionType.CARDINALITY);
					}
				}
			} else {
				results.addNotification(
						"The optionality of this element is set as 'conditional' and no rule has been defined, "
								+ "refer to the specification to check the optionality which applies in the context of this message",
						GazelleErrorCode.CONDITIONAL, hl7Path, null);
			}
			break;
		case NOT_SUPPORTED:
			if (!encoded.isEmpty()) {
				results.addNotification("Element " + name
						+ " is present in the message but specified as not used (X) in the message profile",
						GazelleErrorCode.USAGE, hl7Path, encoded);
				((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setSegmentName(
						currentSegmentName);
				((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setFieldPosition(
						currentFieldNumber);
				return;
			} else {
				results.addAssertion(name + " shall not be present", hl7Path, AssertionType.FORBIDDEN_ELEMENT);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Tests table values for ID, IS, and CE types. An empty list is returned for all other types or if the table name or number is missing.
	 */
	private void testTypeAgainstTable(Type msgComponent, AbstractComponent<?> profileComponent, String profileID) {

		if ((profileComponent.getTable() != null)
				&& (msgComponent.getName().equals("IS") || msgComponent.getName().equals("ID"))) {
			String tableID = makeTableName(profileComponent.getTable());
			String value = ((Primitive) msgComponent).getValue();
			addTableTestResult(profileID, tableID, value);
		} else if (msgComponent.getName().equals("CE")) {
			String value = Terser.getPrimitive(msgComponent, 1, 1).getValue();
			String tableID = Terser.getPrimitive(msgComponent, 3, 1).getValue();
			addTableTestResult(profileID, tableID, value);

			value = Terser.getPrimitive(msgComponent, 4, 1).getValue();
			tableID = Terser.getPrimitive(msgComponent, 6, 1).getValue();
			addTableTestResult(profileID, tableID, value);
		}
	}

	private void addTableTestResult(String profileID, String codeSystem, String value) {
		if ((codeSystem != null) && (value != null)) {
			testValueAgainstTable(profileID, codeSystem, value);
		}
	}

	private void testValueAgainstTable(String profileID, String tableID, String value) {
		ResourceCodeStore store = (ResourceCodeStore) resourceStoreFactory.getCodeStore(profileID, tableID);
		if (store == null) {
			results.addProfileException("No code store found for ID " + tableID, hl7Path, null);
		} else {
			try {
				List<String> validCodes = Arrays.asList(store.getValidCodes(tableID));
				if (!validCodes.isEmpty() && !validCodes.contains(value)) {
					StringBuilder possibleCode = null;
					for (String code : validCodes) {
						if (possibleCode == null) {
							possibleCode = new StringBuilder(code);
						} else {
							possibleCode.append(", ");
							possibleCode.append(code);
						}

					}
					results.addNotification("Code " + value + " not found in table " + tableID
							+ ". Possible values are " + possibleCode.toString(),
							GazelleErrorCode.TABLE_VALUE_NOT_FOUND, hl7Path, value);
					((GazelleHL7Exception) results.getLastNotification()).setHl7tableId(store.getResourceOid() + "#"
							+ tableID.replace("HL7", ""));
				} else if (validCodes.contains(value)) {
					results.addAssertion(value + " is present in table " + tableID, hl7Path, AssertionType.VALUE);
				}
			} catch (ProfileException e) {
				results.addProfileException(
						"An error occurred when retrieving values from table " + tableID + ": " + e.getMessage(),
						hl7Path, null);
			}
		}
	}

	private String makeTableName(String hl7Table) {
		StringBuffer buf = new StringBuffer("HL7");
		String padding = "0";
		if (hl7Table.length() < 4) {
			buf.append(padding);
		}
		if (hl7Table.length() < 3) {
			buf.append(padding);
		}
		if (hl7Table.length() < 2) {
			buf.append(padding);
		}
		buf.append(hl7Table);
		return buf.toString();
	}

	public void testField(Type msgField, Field profileField, Integer fieldIndex, boolean escape, String profileID) {

		// account for MSH 1 & 2 which aren't escaped
		String encoded = null;
		if (!escape && Primitive.class.isAssignableFrom(msgField.getClass())) {
			encoded = ((Primitive) msgField).getValue();
		}

		// test field dataType
		testType(msgField, profileField, encoded, profileID, true);

		// test field components
		if ((profileField.getComponents() > 0) && !profileField.getUsage().equals(Optionality.NOT_SUPPORTED.getKey())) {
			if (Composite.class.isAssignableFrom(msgField.getClass())) {
				Composite msgFieldComposite = (Composite) msgField;
				for (int i = 1; i <= profileField.getComponents(); i++) {
					Component profileComponent = profileField.getComponent(i);
					hl7Path.add(msgField.getName() + "-" + i + "(" + profileComponent.getName() + ")");
					Type messageComponent = null;
					try {
						messageComponent = msgFieldComposite.getComponent(i - 1);
					} catch (DataTypeException e) {
						results.addProfileException(
								"Segment "
										+ currentSegmentName
										+ ": Component "
										+ msgField.getName()
										+ "-"
										+ i
										+ " is defined in the message profile but does not exist in the Java class representing the segment",
								hl7Path, null);
						continue;
					}
					testComponent(messageComponent, profileComponent, profileID);
					hl7Path.removeLastItem();
				}
				checkExtraComponents(msgFieldComposite, profileField.getComponents());
			} else {
				results.addProfileException("Segment " + currentSegmentName + ": Field " + currentFieldNumber
						+ " has type " + msgField.getClass().getName() + " but the message profile defines components",
						hl7Path, null);
			}
		}
	}

	public void testComponent(Type msgComponent, Component profileComponent, String profileID) {
		testType(msgComponent, profileComponent, null, profileID, true);
		// test children
		if ((profileComponent.getSubComponents() > 0) && !profileComponent.getUsage().equals("X")
				&& hasContent(msgComponent)) {
			if (Composite.class.isAssignableFrom(msgComponent.getClass())) {
				Composite msgComposite = (Composite) msgComponent;
				for (int i = 1; i <= profileComponent.getSubComponents(); i++) {
					SubComponent profileSubComponent = profileComponent.getSubComponent(i);
					hl7Path.add(msgComponent.getName() + "-" + i + "(" + profileSubComponent.getName() + ")");
					Type msgSubComposite = null;
					try {
						msgSubComposite = msgComposite.getComponent(i - 1);
					} catch (DataTypeException e) {
						results.addProfileException(
								"Field "
										+ currentSegmentName
										+ "-"
										+ currentFieldNumber
										+ ": SubComponent "
										+ msgComponent.getName()
										+ "-"
										+ i
										+ "is defined in the message profile but does not exist in the Java class representing the message",
								hl7Path, null);
						continue;
					}
					testType(msgSubComposite, profileSubComponent, null, profileID, false);
					hl7Path.removeLastItem();
				}
				checkExtraComponents(msgComposite, profileComponent.getSubComponents());
			} else {
				results.addProfileException("Field " + currentSegmentName + "-" + currentFieldNumber + ": Component "
						+ msgComponent.getName() + " has type " + msgComponent.getClass().getName()
						+ " but the message profile defines components", hl7Path, null);
			}
		}
	}

	/** Tests for extra components (ie any not defined in the profile) */
	private void checkExtraComponents(Composite comp, int numInProfile) {
		StringBuffer extra = new StringBuffer();
		for (int i = numInProfile; i < comp.getComponents().length; i++) {
			String s = null;
			try {
				s = PipeParser.encode(comp.getComponent(i), enc);
			} catch (DataTypeException e) {
				results.addProfileException(
						"Field " + currentSegmentName + "-" + currentFieldNumber + ": message profile defines "
								+ numInProfile + " sub components for component " + comp.getName()
								+ " which is greater than the number of sub components available in the message",
						hl7Path, null);
				continue;
			}
			if ((s != null) && (s.length() > 0)) {
				extra.append(s);
				extra.append(enc.getComponentSeparator());
			}
		}
		if (extra.length() > 0) {
			results.addNotification(
					"The following components are not defined in the message profile but appears in the message: "
							+ extra.toString(), GazelleErrorCode.NOT_ALLOWED, hl7Path, PipeParser.encode(comp, enc));
			((GazelleHL7Exception) results.getLastNotification()).getHl7Exception()
					.setFieldPosition(currentFieldNumber);
			((GazelleHL7Exception) results.getLastNotification()).getHl7Exception().setSegmentName(currentSegmentName);
		}
	}

	/**
	 * Tests a composite against the corresponding section of a profile.
	 */
	/*
	 * public GazelleHL7Exception[] testComposite(Composite comp,
	 * AbstractComposite profile) { }
	 */

	/**
	 * Tests a primitive datatype against a set of rules
	 */
	public void testPrimitive(Primitive msgPrimitive, AbstractComponent<?> profile) {
		ValidationContextImpl defaultValidationContext = new DefaultValidation();
		Collection<PrimitiveTypeRule> rules = defaultValidationContext.getPrimitiveRules(hl7Version,
				profile.getDatatype(), null);
		if (rules != null) {
			for (PrimitiveTypeRule rule : rules) {
				String primitiveToTest = rule.correct(msgPrimitive.getValue());
				ValidationException[] validationExceptions = rule.apply(primitiveToTest);
				if (validationExceptions.length > 0) {
					for (ValidationException e : validationExceptions) {
						results.addNotification(e.getMessageWithoutLocation(), GazelleErrorCode.PRIMITIVE_RULE,
								hl7Path, msgPrimitive.getValue());
					}
				} else {
					results.addAssertion("Rule '" + rule.getDescription() + "' is fullfilled", hl7Path,
							AssertionType.FORMAT);
				}
			}
		}
	}

	/** Returns true is there is content in the given type */
	private boolean hasContent(Type type) {
		boolean has = false;
		String encoded = PipeParser.encode(type, enc);
		if ((encoded != null) && (encoded.length() > 0)) {
			has = true;
		}
		return has;
	}

	private String encodeStructure(Structure structure) {
		try {
			if (Group.class.isAssignableFrom(structure.getClass())) {
				return PipeParser.encode((Group) structure, enc);
			} else if (Segment.class.isAssignableFrom(structure.getClass())) {
				return PipeParser.encode((Segment) structure, enc);
			} else {
				return null;
			}
		} catch (HL7Exception e) {
			return null;
		}

	}
}
