//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.16 at 12:22:11 PM CET 
//

package net.ihe.gazelle.xsd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for AcknowledgmentType.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="AcknowledgmentType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="AL"/>
 *     &lt;enumeration value="NE"/>
 *     &lt;enumeration value="SU"/>
 *     &lt;enumeration value="ER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AcknowledgmentType")
@XmlEnum
public enum AcknowledgmentType {

	AL, NE, SU, ER;

	public String value() {
		return name();
	}

	public static AcknowledgmentType fromValue(String v) {
		return valueOf(v);
	}

}