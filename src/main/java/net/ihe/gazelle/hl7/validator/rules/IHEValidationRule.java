package net.ihe.gazelle.hl7.validator.rules;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.util.Terser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.validation.MessageRule;
import ca.uhn.hl7v2.validation.ValidationException;

public abstract class IHEValidationRule implements MessageRule {

	/**
	 *
	 */
	private static final long serialVersionUID = 1056715525988546393L;
	protected static final int BEGIN_INDEX = 7;

	private static Logger log = LoggerFactory.getLogger(IHEValidationRule.class);

	public abstract int getSeverity();

	/**
	 * This is the method used in GazelleValidator.java. In most of the case it might not need to be overridden
	 *
	 * @param msg
	 * @param hl7path
	 * @return
	 */
	public ValidationException[] test(Message msg, String hl7path) {
		return test(msg);
	}

	@Override public ValidationException[] apply(Message msg) {
		return test(msg);
	}

	protected String formatHl7Path(final String hl7path) {
		String formattedPath = hl7path.replaceAll("\\([^\\)]*[^/]*\\)", "");
		formattedPath = formattedPath.replace('[', '(');
		formattedPath = formattedPath.replace(']', ')');
		return formattedPath;
	}

	protected boolean isMessageProfileIdentifierPresent(Terser terser, String searchedProfileId) {
		try {
			Segment msh = terser.getSegment("/MSH");
			Type[] msh21 = msh.getField(21);
			for (int index = 0; index < msh21.length; index++) {

				String profileId = msh21[index].encode();
				if (profileId.contains(searchedProfileId)) {
					return true;
				} else {
					continue;
				}
			}
		} catch (HL7Exception e) {
			return false;
		}
		return false;
	}
}
