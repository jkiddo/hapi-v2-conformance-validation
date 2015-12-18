package net.ihe.gazelle.hl7.validator.report;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.ihe.gazelle.hl7.messageprofiles.model.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "SummaryResults")
@XmlAccessorType(XmlAccessType.NONE)
public class HL7v2ValidationReport {

	private static Logger log = LoggerFactory.getLogger(HL7v2ValidationReport.class);

	@XmlElement(name = "ValidationResultsOverview")
	private ValidationResultsOverview overview;

	@XmlElement(name = "ValidationCounters")
	private ValidationCounters counters;

	@XmlElement(name = "Resources")
	private UsedResources resources;

	@XmlElement(name = "ValidationResults")
	private ValidationResults results;

	public ValidationResultsOverview getOverview() {
		return overview;
	}

	public void setOverview(ValidationResultsOverview overview) {
		this.overview = overview;
	}

	public ValidationCounters getCounters() {
		if (this.counters == null) {
			this.counters = new ValidationCounters();
		}
		return counters;
	}

	public void setCounters(ValidationCounters counters) {
		this.counters = counters;
	}

	public UsedResources getResources() {
		return resources;
	}

	public void setResources(UsedResources resources) {
		this.resources = resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = new UsedResources();
		this.resources.setResources(resources);
	}

	public ValidationResults getResults() {
		if (this.results == null) {
			this.results = new ValidationResults();
		}
		return results;
	}

	public void setResults(ValidationResults results) {
		this.results = results;
	}

	public enum ValidationStatus {
		ABORTED(1, "ABORTED"),
		INVALID_REQUEST(2, "INVALID REQUEST"),
		FAILED(3, "FAILED"),
		PASSED(4, "PASSED");

		private int level;
		private String status;

		ValidationStatus(int i, String status) {
			this.level = i;
			this.status = status;
		}

		public int getLevel() {
			return this.level;
		}

		public String getStatus() {
			return this.status;
		}
	}

	public static HL7v2ValidationReport createFromXml(String xmlReport){
		ByteArrayInputStream is = new ByteArrayInputStream(xmlReport.getBytes(Charset.forName("UTF-8")));
		try {
			JAXBContext jc = JAXBContext.newInstance(HL7v2ValidationReport.class);
			Unmarshaller u = jc.createUnmarshaller();
			HL7v2ValidationReport object = (HL7v2ValidationReport) u.unmarshal(is);
			return object;
		}catch(Exception e){
			log.error("Unable to unmarshall validation report");
			return null;
		}
	}
}
