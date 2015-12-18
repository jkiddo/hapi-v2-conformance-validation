/*
 * Copyright 2009 IHE International (http://www.ihe.net)
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
package net.ihe.gazelle.hl7.messageprofiles.model;

//JPA imports
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>Class Description : </b>Resource<br>
 * <br>
 * This class describes the Resource object. This class belongs to the InriaHL7MessageProfileRepository module.
 * 
 * Resource possesses the following attributes :
 * <ul>
 * <li><b>id</b> : id corresponding to the Resource</li>
 * <li><b>oid</b> : oid corresponding to the Resource - oid root is 1.3.6.1.4.12559.11.1.3.1</li>
 * <li><b>content</b> : XML file containing the description of the resource</li>
 * </ul>
 * </br> *
 * 
 * @class Resource.java
 * @package net.ihe.gazelle.repository.hl7.inria.model
 * @author Anne-Gaelle Berge / INRIA Rennes IHE development Project
 * @see Aberge@irisa.fr - http://www.ihe-europe.org
 * @version 1.0 - 2009, November 30
 * 
 */

@XmlRootElement(name = "Resource")
@XmlAccessorType(XmlAccessType.NONE)
public class Resource extends CommonProperties implements java.io.Serializable, Comparable<Resource> {

	private static Logger log = LoggerFactory.getLogger(Resource.class);

	/**
	 * Serial ID version of this object
	 */
	private static final long serialVersionUID = 8584096795711547372L;

	// Attributes (existing in database as a Column)

	private Integer id;

	private String hl7Version;

	private String comment;

	/**
	 * used to sort the tables when a profile is linked to several tables the table with the "stronger" weight is first processed and then the decreasing order is used
	 */
	private Integer weight;

	private List<Profile> profiles;

	/*******************************************************************
	 * Constructors
	 * *****************************************************************/

	public Resource() {
		super();
	}

	public Resource(Integer inId) {
		this.id = inId;
	}

	public Resource(String inOid) {
		this.oid = inOid;
	}

	public Resource(String inOid, byte[] inContent) {
		this.oid = inOid;
		setContent(inContent);

	}

	public Resource(Integer inId, String inOid, byte[] inContent) {
		this.id = inId;
		this.oid = inOid;
		setContent(inContent);
	}

	/**********************************************************
	 * Getters and Setters
	 **********************************************************/

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer inId) {
		this.id = inId;
	}

	public void setHl7Version(String hl7Version) {
		this.hl7Version = hl7Version;
	}

	public String getHl7Version() {
		return hl7Version;
	}

	/***********************************************************
	 * Foreign Key for table : hl7_profile_resource
	 ************************************************************/

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	// *********************************************************
	// Overrides for equals and hash code methods
	// *********************************************************

	@Override
	public int compareTo(Resource o) {
		return this.getOid().compareToIgnoreCase(o.getOid());
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getWeight() {
		return weight;
	}

}
