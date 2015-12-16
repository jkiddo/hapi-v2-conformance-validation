//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.16 at 12:22:11 PM CET 
//

package net.ihe.gazelle.xsd;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * Java class for SegmentType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="SegmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{}MessageElementsGroup"/>
 *         &lt;element name="Field" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;group ref="{}LeafMessageElementsGroup"/>
 *                   &lt;element name="Component" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;group ref="{}LeafMessageElementsGroup"/>
 *                             &lt;element name="SubComponent" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;group ref="{}LeafMessageElementsGroup"/>
 *                                     &lt;attGroup ref="{}LeafElementAttributes"/>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attGroup ref="{}LeafElementAttributes"/>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attGroup ref="{}LeafElementAttributes"/>
 *                 &lt;attGroup ref="{}RepeatableElementAttributes"/>
 *                 &lt;attribute name="ItemNo">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                       &lt;pattern value="\d{5}|Z\d{4}"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{}RepeatableElementAttributes"/>
 *       &lt;attribute name="Name" use="required" type="{}SegmentNameType" />
 *       &lt;attribute ref="{}LongName"/>
 *       &lt;attribute ref="{}Usage use="required""/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SegmentType", propOrder = { "impNote", "description",
		"reference", "predicate", "field" })
public class SegmentType {

	@XmlElement(name = "ImpNote")
	protected String impNote;
	@XmlElement(name = "Description")
	protected String description;
	@XmlElement(name = "Reference")
	protected String reference;
	@XmlElement(name = "Predicate")
	protected String predicate;
	@XmlElement(name = "Field", required = true)
	protected List<SegmentType.Field> field;
	@XmlAttribute(name = "Name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "LongName")
	protected String longName;
	@XmlAttribute(name = "Usage", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String usage;
	@XmlAttribute(name = "Min", required = true)
	@XmlSchemaType(name = "nonNegativeInteger")
	protected BigInteger min;
	@XmlAttribute(name = "Max", required = true)
	protected String max;

	/**
	 * Gets the value of the impNote property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getImpNote() {
		return impNote;
	}

	/**
	 * Sets the value of the impNote property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setImpNote(String value) {
		this.impNote = value;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * Gets the value of the reference property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * Sets the value of the reference property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReference(String value) {
		this.reference = value;
	}

	/**
	 * Gets the value of the predicate property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPredicate() {
		return predicate;
	}

	/**
	 * Sets the value of the predicate property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPredicate(String value) {
		this.predicate = value;
	}

	/**
	 * Gets the value of the field property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the field property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getField().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link SegmentType.Field }
	 * 
	 * 
	 */
	public List<SegmentType.Field> getField() {
		if (field == null) {
			field = new ArrayList<SegmentType.Field>();
		}
		return this.field;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the longName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * Sets the value of the longName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLongName(String value) {
		this.longName = value;
	}

	/**
	 * Gets the value of the usage property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUsage() {
		return usage;
	}

	/**
	 * Sets the value of the usage property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUsage(String value) {
		this.usage = value;
	}

	/**
	 * Gets the value of the min property.
	 * 
	 * @return possible object is {@link BigInteger }
	 * 
	 */
	public BigInteger getMin() {
		return min;
	}

	/**
	 * Sets the value of the min property.
	 * 
	 * @param value
	 *            allowed object is {@link BigInteger }
	 * 
	 */
	public void setMin(BigInteger value) {
		this.min = value;
	}

	/**
	 * Gets the value of the max property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMax() {
		return max;
	}

	/**
	 * Sets the value of the max property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMax(String value) {
		this.max = value;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;group ref="{}LeafMessageElementsGroup"/>
	 *         &lt;element name="Component" maxOccurs="unbounded" minOccurs="0">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;group ref="{}LeafMessageElementsGroup"/>
	 *                   &lt;element name="SubComponent" maxOccurs="unbounded" minOccurs="0">
	 *                     &lt;complexType>
	 *                       &lt;complexContent>
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                           &lt;group ref="{}LeafMessageElementsGroup"/>
	 *                           &lt;attGroup ref="{}LeafElementAttributes"/>
	 *                         &lt;/restriction>
	 *                       &lt;/complexContent>
	 *                     &lt;/complexType>
	 *                   &lt;/element>
	 *                 &lt;/sequence>
	 *                 &lt;attGroup ref="{}LeafElementAttributes"/>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *       &lt;attGroup ref="{}LeafElementAttributes"/>
	 *       &lt;attGroup ref="{}RepeatableElementAttributes"/>
	 *       &lt;attribute name="ItemNo">
	 *         &lt;simpleType>
	 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
	 *             &lt;pattern value="\d{5}|Z\d{4}"/>
	 *           &lt;/restriction>
	 *         &lt;/simpleType>
	 *       &lt;/attribute>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "impNote", "description", "reference",
			"predicate", "dataValues", "component" })
	public static class Field {

		@XmlElement(name = "ImpNote")
		protected String impNote;
		@XmlElement(name = "Description")
		protected String description;
		@XmlElement(name = "Reference")
		protected String reference;
		@XmlElement(name = "Predicate")
		protected String predicate;
		@XmlElement(name = "DataValues")
		protected List<SegmentType.Field.DataValues> dataValues;
		@XmlElement(name = "Component")
		protected List<SegmentType.Field.Component> component;
		@XmlAttribute(name = "ItemNo")
		@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
		protected String itemNo;
		@XmlAttribute(name = "Name", required = true)
		protected String name;
		@XmlAttribute(name = "Usage", required = true)
		@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
		protected String usage;
		@XmlAttribute(name = "Datatype", required = true)
		@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
		protected String datatype;
		@XmlAttribute(name = "Length")
		@XmlSchemaType(name = "positiveInteger")
		protected BigInteger length;
		@XmlAttribute(name = "Table")
		@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
		protected String table;
		@XmlAttribute(name = "ConstantValue")
		protected String constantValue;
		@XmlAttribute(name = "Min", required = true)
		@XmlSchemaType(name = "nonNegativeInteger")
		protected BigInteger min;
		@XmlAttribute(name = "Max", required = true)
		protected String max;

		/**
		 * Gets the value of the impNote property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getImpNote() {
			return impNote;
		}

		/**
		 * Sets the value of the impNote property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setImpNote(String value) {
			this.impNote = value;
		}

		/**
		 * Gets the value of the description property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * Sets the value of the description property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setDescription(String value) {
			this.description = value;
		}

		/**
		 * Gets the value of the reference property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getReference() {
			return reference;
		}

		/**
		 * Sets the value of the reference property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setReference(String value) {
			this.reference = value;
		}

		/**
		 * Gets the value of the predicate property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getPredicate() {
			return predicate;
		}

		/**
		 * Sets the value of the predicate property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setPredicate(String value) {
			this.predicate = value;
		}

		/**
		 * Gets the value of the dataValues property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a
		 * snapshot. Therefore any modification you make to the returned list
		 * will be present inside the JAXB object. This is why there is not a
		 * <CODE>set</CODE> method for the dataValues property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getDataValues().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link SegmentType.Field.DataValues }
		 * 
		 * 
		 */
		public List<SegmentType.Field.DataValues> getDataValues() {
			if (dataValues == null) {
				dataValues = new ArrayList<SegmentType.Field.DataValues>();
			}
			return this.dataValues;
		}

		/**
		 * Gets the value of the component property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a
		 * snapshot. Therefore any modification you make to the returned list
		 * will be present inside the JAXB object. This is why there is not a
		 * <CODE>set</CODE> method for the component property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getComponent().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link SegmentType.Field.Component }
		 * 
		 * 
		 */
		public List<SegmentType.Field.Component> getComponent() {
			if (component == null) {
				component = new ArrayList<SegmentType.Field.Component>();
			}
			return this.component;
		}

		/**
		 * Gets the value of the itemNo property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getItemNo() {
			return itemNo;
		}

		/**
		 * Sets the value of the itemNo property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setItemNo(String value) {
			this.itemNo = value;
		}

		/**
		 * Gets the value of the name property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getName() {
			return name;
		}

		/**
		 * Sets the value of the name property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setName(String value) {
			this.name = value;
		}

		/**
		 * Gets the value of the usage property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getUsage() {
			return usage;
		}

		/**
		 * Sets the value of the usage property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setUsage(String value) {
			this.usage = value;
		}

		/**
		 * Gets the value of the datatype property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getDatatype() {
			return datatype;
		}

		/**
		 * Sets the value of the datatype property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setDatatype(String value) {
			this.datatype = value;
		}

		/**
		 * Gets the value of the length property.
		 * 
		 * @return possible object is {@link BigInteger }
		 * 
		 */
		public BigInteger getLength() {
			return length;
		}

		/**
		 * Sets the value of the length property.
		 * 
		 * @param value
		 *            allowed object is {@link BigInteger }
		 * 
		 */
		public void setLength(BigInteger value) {
			this.length = value;
		}

		/**
		 * Gets the value of the table property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getTable() {
			return table;
		}

		/**
		 * Sets the value of the table property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setTable(String value) {
			this.table = value;
		}

		/**
		 * Gets the value of the constantValue property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getConstantValue() {
			return constantValue;
		}

		/**
		 * Sets the value of the constantValue property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setConstantValue(String value) {
			this.constantValue = value;
		}

		/**
		 * Gets the value of the min property.
		 * 
		 * @return possible object is {@link BigInteger }
		 * 
		 */
		public BigInteger getMin() {
			return min;
		}

		/**
		 * Sets the value of the min property.
		 * 
		 * @param value
		 *            allowed object is {@link BigInteger }
		 * 
		 */
		public void setMin(BigInteger value) {
			this.min = value;
		}

		/**
		 * Gets the value of the max property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getMax() {
			return max;
		}

		/**
		 * Sets the value of the max property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setMax(String value) {
			this.max = value;
		}

		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content
		 * contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;group ref="{}LeafMessageElementsGroup"/>
		 *         &lt;element name="SubComponent" maxOccurs="unbounded" minOccurs="0">
		 *           &lt;complexType>
		 *             &lt;complexContent>
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *                 &lt;group ref="{}LeafMessageElementsGroup"/>
		 *                 &lt;attGroup ref="{}LeafElementAttributes"/>
		 *               &lt;/restriction>
		 *             &lt;/complexContent>
		 *           &lt;/complexType>
		 *         &lt;/element>
		 *       &lt;/sequence>
		 *       &lt;attGroup ref="{}LeafElementAttributes"/>
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "impNote", "description",
				"reference", "predicate", "dataValues", "subComponent" })
		public static class Component {

			@XmlElement(name = "ImpNote")
			protected String impNote;
			@XmlElement(name = "Description")
			protected String description;
			@XmlElement(name = "Reference")
			protected String reference;
			@XmlElement(name = "Predicate")
			protected String predicate;
			@XmlElement(name = "DataValues")
			protected List<SegmentType.Field.DataValues> dataValues;
			@XmlElement(name = "SubComponent")
			protected List<SegmentType.Field.Component.SubComponent> subComponent;
			@XmlAttribute(name = "Name", required = true)
			protected String name;
			@XmlAttribute(name = "Usage", required = true)
			@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
			protected String usage;
			@XmlAttribute(name = "Datatype", required = true)
			@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
			protected String datatype;
			@XmlAttribute(name = "Length")
			@XmlSchemaType(name = "positiveInteger")
			protected BigInteger length;
			@XmlAttribute(name = "Table")
			@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
			protected String table;
			@XmlAttribute(name = "ConstantValue")
			protected String constantValue;

			/**
			 * Gets the value of the impNote property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getImpNote() {
				return impNote;
			}

			/**
			 * Sets the value of the impNote property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setImpNote(String value) {
				this.impNote = value;
			}

			/**
			 * Gets the value of the description property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getDescription() {
				return description;
			}

			/**
			 * Sets the value of the description property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setDescription(String value) {
				this.description = value;
			}

			/**
			 * Gets the value of the reference property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getReference() {
				return reference;
			}

			/**
			 * Sets the value of the reference property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setReference(String value) {
				this.reference = value;
			}

			/**
			 * Gets the value of the predicate property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getPredicate() {
				return predicate;
			}

			/**
			 * Sets the value of the predicate property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setPredicate(String value) {
				this.predicate = value;
			}

			/**
			 * Gets the value of the dataValues property.
			 * 
			 * <p>
			 * This accessor method returns a reference to the live list, not a
			 * snapshot. Therefore any modification you make to the returned
			 * list will be present inside the JAXB object. This is why there is
			 * not a <CODE>set</CODE> method for the dataValues property.
			 * 
			 * <p>
			 * For example, to add a new item, do as follows:
			 * 
			 * <pre>
			 * getDataValues().add(newItem);
			 * </pre>
			 * 
			 * 
			 * <p>
			 * Objects of the following type(s) are allowed in the list
			 * {@link SegmentType.Field.DataValues }
			 * 
			 * 
			 */
			public List<SegmentType.Field.DataValues> getDataValues() {
				if (dataValues == null) {
					dataValues = new ArrayList<SegmentType.Field.DataValues>();
				}
				return this.dataValues;
			}

			/**
			 * Gets the value of the subComponent property.
			 * 
			 * <p>
			 * This accessor method returns a reference to the live list, not a
			 * snapshot. Therefore any modification you make to the returned
			 * list will be present inside the JAXB object. This is why there is
			 * not a <CODE>set</CODE> method for the subComponent property.
			 * 
			 * <p>
			 * For example, to add a new item, do as follows:
			 * 
			 * <pre>
			 * getSubComponent().add(newItem);
			 * </pre>
			 * 
			 * 
			 * <p>
			 * Objects of the following type(s) are allowed in the list
			 * {@link SegmentType.Field.Component.SubComponent }
			 * 
			 * 
			 */
			public List<SegmentType.Field.Component.SubComponent> getSubComponent() {
				if (subComponent == null) {
					subComponent = new ArrayList<SegmentType.Field.Component.SubComponent>();
				}
				return this.subComponent;
			}

			/**
			 * Gets the value of the name property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getName() {
				return name;
			}

			/**
			 * Sets the value of the name property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setName(String value) {
				this.name = value;
			}

			/**
			 * Gets the value of the usage property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getUsage() {
				return usage;
			}

			/**
			 * Sets the value of the usage property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setUsage(String value) {
				this.usage = value;
			}

			/**
			 * Gets the value of the datatype property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getDatatype() {
				return datatype;
			}

			/**
			 * Sets the value of the datatype property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setDatatype(String value) {
				this.datatype = value;
			}

			/**
			 * Gets the value of the length property.
			 * 
			 * @return possible object is {@link BigInteger }
			 * 
			 */
			public BigInteger getLength() {
				return length;
			}

			/**
			 * Sets the value of the length property.
			 * 
			 * @param value
			 *            allowed object is {@link BigInteger }
			 * 
			 */
			public void setLength(BigInteger value) {
				this.length = value;
			}

			/**
			 * Gets the value of the table property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getTable() {
				return table;
			}

			/**
			 * Sets the value of the table property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setTable(String value) {
				this.table = value;
			}

			/**
			 * Gets the value of the constantValue property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getConstantValue() {
				return constantValue;
			}

			/**
			 * Sets the value of the constantValue property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setConstantValue(String value) {
				this.constantValue = value;
			}

			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content
			 * contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType>
			 *   &lt;complexContent>
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
			 *       &lt;group ref="{}LeafMessageElementsGroup"/>
			 *       &lt;attGroup ref="{}LeafElementAttributes"/>
			 *     &lt;/restriction>
			 *   &lt;/complexContent>
			 * &lt;/complexType>
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "impNote", "description",
					"reference", "predicate", "dataValues" })
			public static class SubComponent {

				@XmlElement(name = "ImpNote")
				protected String impNote;
				@XmlElement(name = "Description")
				protected String description;
				@XmlElement(name = "Reference")
				protected String reference;
				@XmlElement(name = "Predicate")
				protected String predicate;
				@XmlElement(name = "DataValues")
				protected List<SegmentType.Field.DataValues> dataValues;
				@XmlAttribute(name = "Name", required = true)
				protected String name;
				@XmlAttribute(name = "Usage", required = true)
				@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
				protected String usage;
				@XmlAttribute(name = "Datatype", required = true)
				@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
				protected String datatype;
				@XmlAttribute(name = "Length")
				@XmlSchemaType(name = "positiveInteger")
				protected BigInteger length;
				@XmlAttribute(name = "Table")
				@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
				protected String table;
				@XmlAttribute(name = "ConstantValue")
				protected String constantValue;

				/**
				 * Gets the value of the impNote property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getImpNote() {
					return impNote;
				}

				/**
				 * Sets the value of the impNote property.
				 * 
				 * @param value
				 *            allowed object is {@link String }
				 * 
				 */
				public void setImpNote(String value) {
					this.impNote = value;
				}

				/**
				 * Gets the value of the description property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getDescription() {
					return description;
				}

				/**
				 * Sets the value of the description property.
				 * 
				 * @param value
				 *            allowed object is {@link String }
				 * 
				 */
				public void setDescription(String value) {
					this.description = value;
				}

				/**
				 * Gets the value of the reference property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getReference() {
					return reference;
				}

				/**
				 * Sets the value of the reference property.
				 * 
				 * @param value
				 *            allowed object is {@link String }
				 * 
				 */
				public void setReference(String value) {
					this.reference = value;
				}

				/**
				 * Gets the value of the predicate property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getPredicate() {
					return predicate;
				}

				/**
				 * Sets the value of the predicate property.
				 * 
				 * @param value
				 *            allowed object is {@link String }
				 * 
				 */
				public void setPredicate(String value) {
					this.predicate = value;
				}

				/**
				 * Gets the value of the dataValues property.
				 * 
				 * <p>
				 * This accessor method returns a reference to the live list,
				 * not a snapshot. Therefore any modification you make to the
				 * returned list will be present inside the JAXB object. This is
				 * why there is not a <CODE>set</CODE> method for the dataValues
				 * property.
				 * 
				 * <p>
				 * For example, to add a new item, do as follows:
				 * 
				 * <pre>
				 * getDataValues().add(newItem);
				 * </pre>
				 * 
				 * 
				 * <p>
				 * Objects of the following type(s) are allowed in the list
				 * {@link SegmentType.Field.DataValues }
				 * 
				 * 
				 */
				public List<SegmentType.Field.DataValues> getDataValues() {
					if (dataValues == null) {
						dataValues = new ArrayList<SegmentType.Field.DataValues>();
					}
					return this.dataValues;
				}

				/**
				 * Gets the value of the name property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getName() {
					return name;
				}

				/**
				 * Sets the value of the name property.
				 * 
				 * @param value
				 *            allowed object is {@link String }
				 * 
				 */
				public void setName(String value) {
					this.name = value;
				}

				/**
				 * Gets the value of the usage property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getUsage() {
					return usage;
				}

				/**
				 * Sets the value of the usage property.
				 * 
				 * @param value
				 *            allowed object is {@link String }
				 * 
				 */
				public void setUsage(String value) {
					this.usage = value;
				}

				/**
				 * Gets the value of the datatype property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getDatatype() {
					return datatype;
				}

				/**
				 * Sets the value of the datatype property.
				 * 
				 * @param value
				 *            allowed object is {@link String }
				 * 
				 */
				public void setDatatype(String value) {
					this.datatype = value;
				}

				/**
				 * Gets the value of the length property.
				 * 
				 * @return possible object is {@link BigInteger }
				 * 
				 */
				public BigInteger getLength() {
					return length;
				}

				/**
				 * Sets the value of the length property.
				 * 
				 * @param value
				 *            allowed object is {@link BigInteger }
				 * 
				 */
				public void setLength(BigInteger value) {
					this.length = value;
				}

				/**
				 * Gets the value of the table property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getTable() {
					return table;
				}

				/**
				 * Sets the value of the table property.
				 * 
				 * @param value
				 *            allowed object is {@link String }
				 * 
				 */
				public void setTable(String value) {
					this.table = value;
				}

				/**
				 * Gets the value of the constantValue property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getConstantValue() {
					return constantValue;
				}

				/**
				 * Sets the value of the constantValue property.
				 * 
				 * @param value
				 *            allowed object is {@link String }
				 * 
				 */
				public void setConstantValue(String value) {
					this.constantValue = value;
				}

			}

		}

		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content
		 * contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;attribute name="ExValue" type="{}NonEmptyStringType" />
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "")
		public static class DataValues {

			@XmlAttribute(name = "ExValue")
			protected String exValue;

			/**
			 * Gets the value of the exValue property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getExValue() {
				return exValue;
			}

			/**
			 * Sets the value of the exValue property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setExValue(String value) {
				this.exValue = value;
			}

		}

	}

}
