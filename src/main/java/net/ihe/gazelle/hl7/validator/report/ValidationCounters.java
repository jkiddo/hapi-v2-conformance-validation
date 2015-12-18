package net.ihe.gazelle.hl7.validator.report;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ValidationCounters")
@XmlAccessorType(XmlAccessType.NONE)
public class ValidationCounters {

	@XmlElement(name = "NrOfValidationErrors")
	private Integer nbOfErrors;

	@XmlElement(name = "NrOfValidationWarnings")
	private Integer nbOfWarnings;

	@XmlElement(name = "NrOfValidatedAssertions")
	private Integer nbOfAssertions;

	public Integer getNbOfErrors() {
		return nbOfErrors;
	}

	public void setNbOfErrors(Integer nbOfErrors) {
		this.nbOfErrors = nbOfErrors;
	}

	public Integer getNbOfWarnings() {
		return nbOfWarnings;
	}

	public void setNbOfWarnings(Integer nbOfWarnings) {
		this.nbOfWarnings = nbOfWarnings;
	}

	public Integer getNbOfAssertions() {
		return nbOfAssertions;
	}

	public void setNbOfAssertions(Integer nbOfAssertions) {
		this.nbOfAssertions = nbOfAssertions;
	}

}
