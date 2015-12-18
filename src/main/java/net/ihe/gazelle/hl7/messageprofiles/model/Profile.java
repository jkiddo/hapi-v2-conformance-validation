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

// JPA imports
import java.util.List;

/**
 * <b>Class Description : </b>Profile<br>
 * <br>
 * This class describes the Profile object. This class belongs to the InriaHL7MessageProfileRepository module.
 * 
 * Profile possesses the following attributes :
 * <ul>
 * <li><b>id</b> : id corresponding to the Profile</li>
 * <li><b>oid</b> : oid corresponding to the Profile - oid root is 1.3.6.1.4.12559.11.1.1.</li>
 * <li><b>content</b> : XML file containing the description of the profile</li>
 * </ul>
 * </br> *
 * 
 * @class Profile.java
 * @package net.ihe.gazelle.repository.hl7.inria.model
 * @author Anne-Gaelle Berge / INRIA Rennes IHE development Project
 * @see Aberge@irisa.fr - http://www.ihe-europe.org
 * @version 1.0 - 2009, November 30
 * 
 */

public class Profile extends CommonProperties implements java.io.Serializable, Comparable<Profile> {

	/**
	 * Serial ID version of this object
	 */
	private static final long serialVersionUID = 9110924423557888115L;

	private Integer id;

	private String javaPackage;

	private List<Resource> resources;

	/*******************************************************************
	 * Constructors
	 * *****************************************************************/

	public Profile() {
		super();
	}

	public Profile(Integer inId) {
		this.id = inId;
	}

	public Profile(String inOid) {
		this.oid = inOid;
	}

	public Profile(String inOid, byte[] profileContent) {
		this.oid = inOid;
		if (profileContent != null) {
			this.content = profileContent.clone();
		}
	}

	public Profile(Integer inId, String inOid, String inContent) {
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

	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	public String getJavaPackage() {
		return javaPackage;
	}

	/***********************************************************
	 * Foreign Key for table : hl7_profile_resource
	 ************************************************************/

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	@Override
	public int compareTo(Profile o) {
		return this.getOid().compareToIgnoreCase(o.getOid());
	}

}
