package net.ihe.gazelle.hl7.validator.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ca.uhn.hl7v2.conf.ProfileException;
import ca.uhn.hl7v2.conf.store.AbstractCodeStore;

public class ResourceCodeStore extends AbstractCodeStore {

	private static Logger log = LoggerFactory.getLogger(ResourceCodeStore.class);

	private static final int CODE_MAX_LENGTH = 4;
	private XMLReader xmlReader;
	private String resourceOid;

	public ResourceCodeStore(byte[] resourceContent, String oid) throws Exception {
		if (resourceContent != null) {
			xmlReader = new XMLReader(resourceContent.clone());
		}
		this.resourceOid = oid;
	}

	@Override
	public String[] getValidCodes(String codeSystem) throws ProfileException {
		if ((codeSystem == null) || (codeSystem.length() < CODE_MAX_LENGTH)) {
			return null;
		} else {
			String tableId = codeSystem.substring(codeSystem.length() - CODE_MAX_LENGTH);
			return xmlReader.getValidCodesForTable(tableId);
		}
	}

	@Override
	public boolean knowsCodes(String codeSystem) {
		if ((codeSystem == null) || (codeSystem.length() < CODE_MAX_LENGTH)) {
			return false;
		} else {
			String tableId = codeSystem.substring(codeSystem.length() - CODE_MAX_LENGTH);
			return xmlReader.tableExists(tableId);
		}
	}

	public void setResourceOid(String resourceOid) {
		this.resourceOid = resourceOid;
	}

	public String getResourceOid() {
		return resourceOid;
	}

	public class XMLReader extends DefaultHandler {
		private boolean searchedTableExists;
		private boolean onlyCheckTableExists;
		private String searchedTableID;
		private List<String> validCodes;
		private byte[] resourceContent;

		public XMLReader(byte[] resourceContent) {
			if (resourceContent != null) {
				this.resourceContent = resourceContent.clone();
			}
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) {
			if (name.equals("hl7table") && attributes.getValue("id").equals(searchedTableID)) {
				searchedTableExists = true;
			}
			if (!onlyCheckTableExists && searchedTableExists && name.equals("tableElement")
					&& !attributes.getValue("usage").equals("Forbidden")) {
				validCodes.add(attributes.getValue("code"));
			}
		}

		@Override
		public void endElement(String uri, String localName, String name) {
			if (!onlyCheckTableExists && name.equals("hl7table")) {
				searchedTableExists = false;
			}
		}

		public boolean tableExists(String tableID) {
			searchedTableExists = false;
			onlyCheckTableExists = true;
			searchedTableID = tableID;
			try {
				parse();
			} catch (Exception e) {
				log.error(e.getMessage());
				return false;
			}
			return searchedTableExists;
		}

		public String[] getValidCodesForTable(String tableID) throws ProfileException {
			searchedTableID = tableID;
			searchedTableExists = false;
			onlyCheckTableExists = false;
			validCodes = new ArrayList<String>();
			try {
				parse();
			} catch (ParserConfigurationException e) {
				log.error(e.getMessage(), e);
				throw new ProfileException(e.getMessage(), e);
			} catch (SAXException e) {
				log.error(e.getMessage(), e);
				throw new ProfileException(e.getMessage(), e);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw new ProfileException(e.getMessage(), e);
			}
			return validCodes.toArray(new String[0]);
		}

		public void parse() throws ParserConfigurationException, SAXException, IOException {
			SAXParserFactory saxFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxFactory.newSAXParser();
			ByteArrayInputStream bais = new ByteArrayInputStream(resourceContent);
			Reader reader = new InputStreamReader(bais, Charset.forName("UTF-8"));
			InputSource inputSource = new InputSource(reader);
			inputSource.setEncoding("UTF-8");
			saxParser.parse(inputSource, this);
		}
	}

}
