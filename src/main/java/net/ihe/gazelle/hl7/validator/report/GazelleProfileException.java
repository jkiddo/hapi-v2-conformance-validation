package net.ihe.gazelle.hl7.validator.report;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.ihe.gazelle.hl7.validator.core.GazelleHL7Exception;
import net.ihe.gazelle.hl7.validator.core.PathBuilder;
import ca.uhn.hl7v2.HL7Exception;

@XmlRootElement(name = "ProfileException")
@XmlAccessorType(XmlAccessType.NONE)
public class GazelleProfileException extends GazelleHL7Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3827218270945165049L;

	public GazelleProfileException() {
		super();
	}

	public GazelleProfileException(HL7Exception exception, PathBuilder pathBuilder, String inValue) {
		super(exception, pathBuilder, inValue);
		// TODO Auto-generated constructor stub
	}

	public GazelleProfileException(String message, PathBuilder pathBuilder, String value) {
		super(message, pathBuilder, value);
	}

}
