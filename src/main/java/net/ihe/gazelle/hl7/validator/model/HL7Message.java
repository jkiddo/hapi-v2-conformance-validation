/*
 * Copyright 2012 IHE International (http://www.ihe.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.ihe.gazelle.hl7.validator.model;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import net.ihe.gazelle.hl7.validator.report.HL7v2ValidationReport;

public class HL7Message implements Serializable {

	private static final long serialVersionUID = -315076050894161909L;
	public static final String ERROR = "ERROR";
	public static final String WARNING = "WARNING";
	public static final String IGNORE = "IGNORE";

	private Integer id;

	private String message;

	private String oid;

	private String hl7Version;

	private byte[] validationContext;

	private byte[] metadata;

	private String detailedResults;

	private Date validationDate;

	
	private String encoding;
	

	/**
	 * for use by admin only, indicates if the validation of this message has raised profile exceptions or not
	 */
	private boolean raisesProfileExceptions;

	String callerIP; // Stores the IP Address of the webservice caller

	String testResult; // Stores the result of the validation for easy retrieve
	// in the database.

	String profileOid;

	private String messageStructure;

	private String length;

	private String datatype;

	private String data;

	private HL7v2ValidationReport report;

	/**
	 * Constructors
	 */
	public HL7Message() {

	}

	/**
	 * Getters and Setters
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String messageOID) {
		this.oid = messageOID;
	}

	public String getHl7Version() {
		return hl7Version;
	}

	public void setHl7Version(String referencedStandard) {
		this.hl7Version = referencedStandard;
	}

	public String getMetadata() {
		if (metadata != null) {
			return new String(metadata, Charset.forName("UTF-8"));
		} else {
			return null;
		}
	}

	public void setMetadata(String messageMetaData) {
		if (messageMetaData != null) {
			this.metadata = messageMetaData.getBytes(Charset.forName("UTF-8"));
		} else {
			this.metadata = null;
		}
	}

	public String getDetailedResults() {
		return detailedResults;
	}

	public void setDetailedResults(String detailedResults) {
		this.detailedResults = detailedResults;
	}

	public String getCallerIP() {
		return callerIP;
	}

	public void setCallerIP(String callerIP) {
		this.callerIP = callerIP;
	}

	public Date getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(Date lastChanged) {
		this.validationDate = lastChanged;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getProfileOid() {
		return profileOid;
	}

	public void setProfileOid(String profileOid) {
		this.profileOid = profileOid;
	}

	public void setValidationContext(String validationContext) {
		if (validationContext != null) {
			this.validationContext = validationContext.getBytes(Charset.forName("UTF-8"));
		} else {
			this.validationContext = null;
		}
	}

	public String getValidationContextAsString() {
		if (this.validationContext != null) {
			return new String(this.validationContext, Charset.forName("UTF-8"));
		} else {
			return null;
		}
	}

	public byte[] getValidationContext() {
		return validationContext;
	}

	public String getMessageStructure() {
		return messageStructure;
	}

	public void setMessageStructure(String messageStructure) {
		this.messageStructure = messageStructure;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setLength(String error2) {
		this.length = error2;
	}

	public String getLength() {
		return length;
	}

	public void setRaisesProfileExceptions(boolean raisesProfileExceptions) {
		this.raisesProfileExceptions = raisesProfileExceptions;
	}

	public boolean isRaisesProfileExceptions() {
		return raisesProfileExceptions;
	}

	public Integer getId() {
		return id;
	}

	public HL7v2ValidationReport getReport() {
		return report;
	}

	public void setReport(HL7v2ValidationReport report) {
		this.report = report;
	}

	public void setDetailedResults() {
		if (this.report != null) {
			this.detailedResults = this.report.toString();
		}
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		HL7Message other = (HL7Message) obj;
		if (oid == null) {
			if (other.oid != null) {
				return false;
			}
		} else if (!oid.equals(other.oid)) {
			return false;
		}
		return true;
	}
}
