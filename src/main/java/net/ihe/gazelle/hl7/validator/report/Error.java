package net.ihe.gazelle.hl7.validator.report;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.ihe.gazelle.hl7.validator.core.GazelleErrorCode;
import net.ihe.gazelle.hl7.validator.core.GazelleHL7Exception;
import net.ihe.gazelle.hl7.validator.core.PathBuilder;
import ca.uhn.hl7v2.HL7Exception;

@XmlRootElement(name = "Error")
@XmlAccessorType(XmlAccessType.NONE)
public class Error extends GazelleHL7Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7091157656274797386L;

	public Error() {
		super();
	}

	public Error(String message, PathBuilder pathBuilder, String value) {
		super(message, pathBuilder, value);
	}

	public Error(String message, String tableId, PathBuilder pathBuilder, String inValue) {
		super(message, tableId, pathBuilder, inValue);
	}

	public Error(HL7Exception exception, PathBuilder pathBuilder, String inValue) {
		super(exception, pathBuilder, inValue);
	}

	public Error(Throwable cause) {
		super(cause);
	}

	public Error(String message, GazelleErrorCode errorCode, PathBuilder pathBuilder, String inValue) {
		super(message, errorCode, pathBuilder, inValue);
	}

	public Error(String message, int errorCode, PathBuilder pathBuilder, String inValue) {
		super(message, errorCode, pathBuilder, inValue);
	}

}
