package net.ihe.gazelle.hl7.validator.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.ihe.gazelle.hl7.validator.report.HL7v2ValidationReport.ValidationStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "ValidationResultsOverview")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationResultsOverview {

	private static Logger log = LoggerFactory.getLogger(ValidationResultsOverview.class);

	private final String SDF_DATE = "yyyy, MM dd";
	private final String SDF_TIME = "hh:mm (aa)";
	private static final String TOOL_NAME = "Gazelle HL7 Validator";
	private static final String DISCLAIMER = "The GazelleHL7v2Validator is an experimental system. IHE-Europe assumes no responsibility whatsoever "
			+ "for its use by other parties, and makes no guarantees, expressed or implied, about its quality, reliability, or "
			+ "any other characteristic. We would appreciate acknowledgement if the service is used. "
			+ "Bug tracking service is available at http://gazelle.ihe.net/jira/browse/HLVAL";

	@XmlTransient
	private Date validationDateTime;

	@XmlElement(name = "ProfileOID")
	private String profileOid;

	@XmlElement(name = "ProfileRevision")
	private String profileRevision;

	@XmlElement(name = "MessageOID")
	private String messageOid;

	@XmlElement(name = "ValidationAbortedReason")
	private String validationAbortedReason;

	@XmlTransient
	private ValidationStatus validationStatus;

	public ValidationResultsOverview() {

	}

	public ValidationResultsOverview(String profileOid, String messageOid) {
		this.profileOid = profileOid;
		this.messageOid = messageOid;
		this.validationDateTime = new Date();
	}

	public Date getValidationDateTime() {
		return validationDateTime;
	}

	public void setValidationDateTime(Date validationDateTime) {
		this.validationDateTime = validationDateTime;
	}

	@XmlElement(name = "ValidationDate")
	public String getValidationDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(SDF_DATE, Locale.ENGLISH);
		return sdf.format(validationDateTime);
	}

	@XmlElement(name = "ValidationTime")
	public String getValidationTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(SDF_TIME, Locale.ENGLISH);
		return sdf.format(validationDateTime);
	}

	@XmlElement(name = "ValidationServiceName")
	public String getValidationServiceName() {
		return TOOL_NAME;
	}

	public String getProfileOid() {
		return profileOid;
	}

	public void setProfileOid(String profileOid) {
		this.profileOid = profileOid;
	}

	public String getProfileRevision() {
		return profileRevision;
	}

	public void setProfileRevision(String profileRevision) {
		this.profileRevision = profileRevision;
	}

	public String getMessageOid() {
		return messageOid;
	}

	public void setMessageOid(String messageOid) {
		this.messageOid = messageOid;
	}

	@XmlElement(name = "Disclaimer")
	public String getDisclaimer() {
		return DISCLAIMER;
	}

	@XmlElement(name = "ValidationTestResult")
	public String getValidationTestResult() {
		return validationStatus.getStatus();
	}

	public String getValidationAbortedReason() {
		return validationAbortedReason;
	}

	public void setValidationAbortedReason(String validationAbortedReason) {
		this.validationAbortedReason = validationAbortedReason;
	}

	public ValidationStatus getValidationStatus() {
		return validationStatus;
	}

	public void setValidationStatus(ValidationStatus validationStatus) {
		this.validationStatus = validationStatus;
	}
}
