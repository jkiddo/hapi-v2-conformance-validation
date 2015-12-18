package net.ihe.gazelle.hl7.validator.report;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.ihe.gazelle.hl7.validator.core.GazelleErrorCode;
import net.ihe.gazelle.hl7.validator.core.GazelleHL7Exception;
import net.ihe.gazelle.hl7.validator.core.PathBuilder;
import ca.uhn.hl7v2.HL7Exception;

@XmlRootElement(name = "Warning")
@XmlAccessorType(XmlAccessType.NONE)
public class Warning extends GazelleHL7Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -314692630571883562L;

	public Warning() {
		super();
	}

	public Warning(String message, PathBuilder pathBuilder, String value) {
		super(message, pathBuilder, value);
	}

	public Warning(String message, String tableId, PathBuilder pathBuilder, String inValue) {
		super(message, tableId, pathBuilder, inValue);
	}

	public Warning(HL7Exception exception, PathBuilder pathBuilder, String inValue) {
		super(exception, pathBuilder, inValue);
	}

	public Warning(Throwable cause) {
		super(cause);
	}

	public Warning(String message, GazelleErrorCode errorCode, PathBuilder pathBuilder, String inValue) {
		super(message, errorCode, pathBuilder, inValue);
	}

	public Warning(String message, int errorCode, PathBuilder pathBuilder, String inValue) {
		super(message, errorCode, pathBuilder, inValue);
	}

}
