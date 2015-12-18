package net.ihe.gazelle.hl7.validator.report;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.ihe.gazelle.hl7.validator.core.GazelleErrorCode;
import net.ihe.gazelle.hl7.validator.core.GazelleHL7Assertion;
import net.ihe.gazelle.hl7.validator.core.GazelleHL7Assertion.AssertionType;
import net.ihe.gazelle.hl7.validator.core.PathBuilder;
import net.ihe.gazelle.hl7.validator.model.HL7Message;
import ca.uhn.hl7v2.HL7Exception;

@XmlRootElement(name = "ValidationResults")
@XmlAccessorType(XmlAccessType.NONE)
public class ValidationResults {

	private static final Integer INIT_VALUE = 0;

	@XmlTransient
	private Integer errorCounter = INIT_VALUE;

	@XmlTransient
	private Integer warningCounter = INIT_VALUE;

	@XmlTransient
	private Integer reportCounter = INIT_VALUE;

	@XmlTransient
	private Integer exceptionCounter = INIT_VALUE;

	@XmlElements({ @XmlElement(name = "Warning", type = Warning.class),
			@XmlElement(name = "Error", type = Error.class),
			@XmlElement(name = "Report", type = GazelleHL7Assertion.class),
			@XmlElement(name = "ProfileException", type = GazelleProfileException.class) })
	private List<Object> notifications;

	private boolean lengthIsAnError;
	private boolean notFountValueIsAnError;
	private boolean wrongDatatypeIsAnError;
	private boolean wrongSequenceIsAnError;

	public ValidationResults() {
		this.notifications = new ArrayList<Object>();
		this.lengthIsAnError = true;
		this.notFountValueIsAnError = false;
		this.wrongDatatypeIsAnError = true;
		this.wrongSequenceIsAnError = true;
	}

	public ValidationResults(String actionOnLength, String actionOnValueNotFound, String actionOnDatatype,
			String actionOnSequenceError) {
		if ((actionOnLength != null) && actionOnLength.equals(HL7Message.ERROR)) {
			this.lengthIsAnError = true;
		} else {
			this.lengthIsAnError = false;
		}
		if ((actionOnValueNotFound != null) && actionOnValueNotFound.equals(HL7Message.ERROR)) {
			this.notFountValueIsAnError = true;
		} else {
			this.notFountValueIsAnError = false;
		}
		if ((actionOnDatatype != null) && actionOnDatatype.equals(HL7Message.ERROR)) {
			this.wrongDatatypeIsAnError = true;
		} else {
			this.wrongDatatypeIsAnError = false;
		}
		if ((actionOnSequenceError != null) && actionOnSequenceError.equals(HL7Message.ERROR)) {
			this.wrongSequenceIsAnError = true;
		} else {
			this.wrongSequenceIsAnError = false;
		}
		this.notifications = new ArrayList<Object>();
	}

	public void addNotification(String message, PathBuilder pathBuilder, String value) {
		this.notifications.add(new Error(message, pathBuilder, value));
		this.errorCounter++;
	}

	public void addNotification(String message, String tableId, PathBuilder pathBuilder, String inValue) {
		this.notifications.add(new Error(message, tableId, pathBuilder, inValue));
		this.errorCounter++;
	}

	public void addNotification(HL7Exception exception, PathBuilder pathBuilder, String inValue) {
		if (createError(GazelleErrorCode.errorCodeFor(exception.getErrorCode()))) {
			this.notifications.add(new Error(exception, pathBuilder, inValue));
			this.errorCounter++;
		} else {
			this.notifications.add(new Warning(exception, pathBuilder, inValue));
			this.warningCounter++;
		}
	}

	public void addNotification(Throwable cause) {
		this.notifications.add(new Error(cause));
		this.errorCounter++;
	}

	public void addNotification(String message, GazelleErrorCode errorCode, PathBuilder pathBuilder, String inValue) {
		if (createError(errorCode)) {
			this.notifications.add(new Error(message, errorCode, pathBuilder, inValue));
			this.errorCounter++;
		} else {
			this.notifications.add(new Warning(message, errorCode, pathBuilder, inValue));
			this.warningCounter++;
		}
	}

	public void addNotification(String message, int errorCode, PathBuilder pathBuilder, String inValue) {
		if (createError(GazelleErrorCode.errorCodeFor(errorCode))) {
			this.notifications.add(new Error(message, errorCode, pathBuilder, inValue));
			this.errorCounter++;
		} else {
			this.notifications.add(new Warning(message, errorCode, pathBuilder, inValue));
			this.warningCounter++;
		}
	}

	public void addAssertion(String inAssertion, PathBuilder inPath, AssertionType inType) {
		this.notifications.add(new GazelleHL7Assertion(inAssertion, inPath, inType));
		this.reportCounter++;
	}

	public void addProfileException(String message, PathBuilder pathBuilder, String value) {
		this.notifications.add(new GazelleProfileException(message, pathBuilder, value));
		this.exceptionCounter++;
	}

	/**
	 * Returns true if we need to create a new Error, otherwise, we create a warning
	 * 
	 * @param errorCode
	 * @return
	 */
	private boolean createError(GazelleErrorCode errorCode) {
		switch (errorCode) {
		case LENGTH_ERROR:
			return lengthIsAnError;
		case TABLE_VALUE_NOT_FOUND:
			return notFountValueIsAnError;
		case DATA_TYPE_ERROR:
			return wrongDatatypeIsAnError;
		case SEGMENT_SEQUENCE_ERROR:
			return wrongSequenceIsAnError;
		case CONDITIONAL:
			return false;
		default:
			return true;
		}
	}

	public List<Object> getNotifications() {
		if (this.notifications == null) {
			this.notifications = new ArrayList<Object>();
		}
		return notifications;
	}

	public void setNotifications(List<Object> notifications) {
		this.notifications = notifications;
	}

	public Object getLastNotification() {
		if (!this.notifications.isEmpty()) {
			return this.notifications.get(this.notifications.size() - 1);
		} else {
			return null;
		}
	}

	public String printErrors() {
		StringBuffer errors = new StringBuffer();
		for (Object notification : getNotifications()) {
			if (notification instanceof Error
					&& !((Error) notification).getGazelleErrorCode().equals(GazelleErrorCode.LENGTH_ERROR)
					&& !((Error) notification).getGazelleErrorCode().equals(GazelleErrorCode.TABLE_VALUE_NOT_FOUND)) {
				Error error = (Error) notification;

				errors.append(error.getHl7Path());
				errors.append(" : ");
				errors.append(error.getMessage());
				errors.append("\n");
			}
		}
		return errors.toString();
	}

	public String printProfileException() {
		StringBuffer errors = new StringBuffer();
		for (Object notification : getNotifications()) {
			if (notification instanceof GazelleProfileException) {
				GazelleProfileException exception = (GazelleProfileException) notification;
				errors.append(exception.getFailureType());
				errors.append(":");
				errors.append(exception.getDescription());
				errors.append(" (");
				errors.append(exception.getHl7Path());
				errors.append(" )");
				errors.append("<br/>");
			}
		}
		return errors.toString();
	}

	public Integer getErrorCounter() {
		return errorCounter;
	}

	public Integer getWarningCounter() {
		return warningCounter;
	}

	public Integer getReportCounter() {
		return reportCounter;
	}

	public Integer getExceptionCounter() {
		return exceptionCounter;
	}
}
