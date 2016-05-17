package net.ihe.gazelle.hl7.validator.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class description <b>GazelleHL7Rule</b>
 * 
 * This class is used to store the rules which are applied to a message during its validation in order to report them to the final user.
 * 
 * @package net.ihe.gazelle.hl7.validator.core
 * @class GazelleHL7Rule.java
 * @author Anne-Gaëlle Bergé / IHE Europe
 * @see anne-gaelle.berge@ihe-europe.net
 * 
 */
@XmlRootElement(name = "Report")
@XmlAccessorType(XmlAccessType.NONE)
public class GazelleHL7Assertion {

	@XmlElement(name = "Description")
	private String assertion;

	@XmlElement(name = "Location")
	private String path;

	@XmlElement(name = "Type")
	private AssertionType type;

	@XmlEnum
	public enum AssertionType {
		@XmlEnumValue(value = "Length")
		LENGTH("Length"),
		@XmlEnumValue(value = "Cardinality")
		CARDINALITY("Cardinality"),
		@XmlEnumValue(value = "Required element")
		REQUIRED_ELEMENT("Required element"),
		@XmlEnumValue(value = "Forbidden element")
		FORBIDDEN_ELEMENT("Forbidden element"),
		@XmlEnumValue(value = "Optional element")
		OPTIONAL_ELEMENT("Optional element"),
		@XmlEnumValue(value = "Datatype")
		DATATYPE("Datatype"),
		@XmlEnumValue(value = "Format")
		FORMAT("Format"),
		@XmlEnumValue(value = "Value")
		VALUE("Value");

		private String label;

		AssertionType(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

	public GazelleHL7Assertion() {

	}

	public GazelleHL7Assertion(String inAssertion, PathBuilder inPath, AssertionType inType) {
		this.assertion = inAssertion;
		this.path = inPath.toString();
		this.type = inType;
	}

	/**
	 * Get the assertion.
	 *
	 * @return The assertion.
     */
	public String getAssertion() {
		return assertion;
	}

	/**
	 * Get the path.
	 *
	 * @return The path.
     */
	public String getPath() {
		return path;
	}

	/**
	 * Get the assertion type.
	 *
	 * @return The assertion type.
     */
	public AssertionType getType() {
		return type;
	}

	@Override
	public String toString() {
		return this.path + ": " + this.assertion;
	}
}
