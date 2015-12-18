package net.ihe.gazelle.hl7.validator.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import ca.uhn.hl7v2.HL7Exception;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class GazelleHL7Exception {

	/**
	 * if exception is located in an HL7 table, give the oid/#hl7TableID string
	 */
	@XmlElement(name = "HL7Table")
	private String hl7tableId;
	/**
	 * Path to the exception
	 */
	@XmlElement(name = "Location")
	private String hl7Path;
	/**
	 * value causing the exception
	 */
	@XmlElement(name = "Value")
	private String value;

	@XmlElement(name = "Description")
	private String description;

	@XmlTransient
	private GazelleErrorCode gazelleErrorCode;

	@XmlTransient
	private HL7Exception hl7Exception;

	public GazelleHL7Exception() {
		super();
	}

	public GazelleHL7Exception(String message, PathBuilder pathBuilder, String value) {
		this.hl7Exception = new HL7Exception(message);
		if (pathBuilder != null) {
			this.hl7Path = pathBuilder.toString();
		}
		this.value = value;
		this.description = getMessage();
	}

	public GazelleHL7Exception(String message, String tableId, PathBuilder pathBuilder, String inValue) {
		this.hl7Exception = new HL7Exception(message);
		this.hl7tableId = tableId;
		this.value = inValue;
		if (pathBuilder != null) {
			this.hl7Path = pathBuilder.toString();
		}
		this.description = getMessage();
	}

	public GazelleHL7Exception(HL7Exception exception, PathBuilder pathBuilder, String inValue) {
		this.hl7Exception = new HL7Exception(exception.getMessage());
		this.hl7Exception.setLocation(exception.getLocation());
		if (pathBuilder != null) {
			this.hl7Path = pathBuilder.toString();
		}
		this.setGazelleErrorCode(exception.getErrorCode());
		this.value = inValue;
		this.description = getMessage();
	}

	public GazelleHL7Exception(Throwable cause) {
		this.hl7Exception = new HL7Exception(cause);
		this.description = getMessage();
	}

	public GazelleHL7Exception(String message, GazelleErrorCode errorCode, PathBuilder pathBuilder, String inValue) {
		this.hl7Exception = new HL7Exception(message);
		this.gazelleErrorCode = errorCode;
		if (pathBuilder != null) {
			this.hl7Path = pathBuilder.toString();
		}
		this.value = inValue;
		this.description = getMessage();
	}

	public GazelleHL7Exception(String message, int errorCode, PathBuilder pathBuilder, String inValue) {
		this.hl7Exception = new HL7Exception(message);
		setGazelleErrorCode(errorCode);
		if (pathBuilder != null) {
			this.hl7Path = pathBuilder.toString();
		}
		this.value = inValue;
		this.description = getMessage();
	}


	public String getMessage() {
		if (this.hl7Exception != null) {
			String message = this.hl7Exception.getMessage();
			int locationIndex = message.indexOf(": Segment:");
			if (locationIndex > 0) {
				message = message.substring(0, locationIndex);
			}
			return message;
		} else {
			return null;
		}
	}

	public String getDescription(){
		return description;
	}

	public void setHl7tableId(String hl7tableId) {
		this.hl7tableId = hl7tableId;
	}

	public String getHl7tableId() {
		return hl7tableId;
	}

	public void setHl7Path(String hl7Path) {
		this.hl7Path = hl7Path;
	}

	public String getHl7Path() {
		if (this.hl7Path != null) {
			return hl7Path;
		} else if ((this.hl7Exception != null) && (this.hl7Exception.getLocation() != null)) {
			return this.hl7Exception.getLocation().toString();
		} else {
			return null;
		}
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public GazelleErrorCode getGazelleErrorCode() {
		if (this.gazelleErrorCode == null) {
			this.gazelleErrorCode = GazelleErrorCode.UNKNOWN;
		}
		return gazelleErrorCode;
	}

	public void setGazelleErrorCode(GazelleErrorCode gazelleErrorCode) {
		if (gazelleErrorCode != null) {
			this.gazelleErrorCode = gazelleErrorCode;
		} else {
			this.gazelleErrorCode = GazelleErrorCode.UNKNOWN;
		}
	}

	public void setGazelleErrorCode(int code) {
		this.gazelleErrorCode = GazelleErrorCode.errorCodeFor(code);
	}

	@XmlElement(name = "Type")
	public String getFailureType() {
		return this.getGazelleErrorCode().getMessage();
	}

	public HL7Exception getHl7Exception() {
		return hl7Exception;
	}

	public void setHl7Exception(HL7Exception hl7Exception) {
		this.hl7Exception = hl7Exception;
	}

}
