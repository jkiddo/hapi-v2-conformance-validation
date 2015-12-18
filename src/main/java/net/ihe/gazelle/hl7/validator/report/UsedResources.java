package net.ihe.gazelle.hl7.validator.report;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import net.ihe.gazelle.hl7.messageprofiles.model.Resource;

/**
 * 
 * @author aberge
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class UsedResources {

	@XmlElement(name = "Resource")
	private List<Resource> resources;

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
}
