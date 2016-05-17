package net.ihe.gazelle.hl7.messageprofiles.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class CommonProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6189770738035000500L;

	private static Logger log = LoggerFactory.getLogger(CommonProperties.class);

	@XmlElement(name = "oid")
	protected String oid;

	protected byte[] content;

	protected Date lastChanged;

	protected String lastModifier;

	protected boolean importedWithErrors;

	@XmlElement(name = "revision")
	protected String revision;

	public CommonProperties() {

	}

	public String getOid() {
		return oid;
	}

	public void setOid(final String oid) {
		this.oid = oid;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(final byte[] content) {
		if (content != null) {
			this.content = content.clone();
		}
	}

	public Date getLastChanged() {
		return lastChanged;
	}

	public void setLastChanged(final Date lastChanged) {
		this.lastChanged = lastChanged;
	}

	public String getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(final String lastModifier) {
		this.lastModifier = lastModifier;
	}

	public boolean isImportedWithErrors() {
		return importedWithErrors;
	}

	public void setImportedWithErrors(final boolean importedWithErrors) {
		this.importedWithErrors = importedWithErrors;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(final String revision) {
		this.revision = revision;
	}

	public void setContent(final String inContent) {
		if (inContent != null) {
			try {
				this.content = inContent.getBytes("UTF8");
			} catch (final UnsupportedEncodingException e) {
				this.content = null;
				log.error(e.getMessage(), e);
			}
		} else {
			this.content = null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((oid == null) ? 0 : oid.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CommonProperties other = (CommonProperties) obj;
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
