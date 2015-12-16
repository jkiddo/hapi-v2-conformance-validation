package org.jkiddo.hapi.v2x.ihe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.ihe.gazelle.xsd.HL7V2XConformanceProfile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.conf.ProfileException;
import ca.uhn.hl7v2.conf.parser.ProfileParser;
import ca.uhn.hl7v2.conf.spec.RuntimeProfile;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

public class IHEProfileFileStore implements TypedProfileStore {

	private final Logger logger = LoggerFactory
			.getLogger(IHEProfileFileStore.class);
	private final Map<String, String> profileToContent = new HashMap<String, String>();
	private final ProfileParser pp = new ProfileParser(true);

	private final JAXBContext jaxbContext = JAXBContext
			.newInstance(HL7V2XConformanceProfile.class);
	private final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	private final Marshaller marshaller = jaxbContext.createMarshaller();

	public IHEProfileFileStore(File root) throws JAXBException {

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		logger.debug("Listing files from " + root.getAbsolutePath());
		File[] files = root.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				logger.debug("Found file: " + name);
				return name.endsWith("xml");
			}
		});

		for (File f : files) {
			try {
				logger.debug("Trying to parse " + f.getName());
				FileInputStream fis = new FileInputStream(f);
				String content = CharStreams.toString(new InputStreamReader(
						fis, Charsets.UTF_8));
				Closeables.closeQuietly(fis);
				RuntimeProfile profile = pp.parse(content);
				profileToContent.put(f.getName(), content);
				logger.debug(f.getName() + " succesfully parsed");
			} catch (Exception e) {
				logger.debug("File " + f.getName() + " could not be parsed");
			}
		}
	}

	@Override
	public String getProfile(String ID) throws IOException {
		return profileToContent.get(ID + ".xml");
	}

	@Override
	public void persistProfile(String ID, String profile) throws IOException {
		throw new RuntimeException("No persistent storage available");
	}

	@Override
	public HL7V2XConformanceProfile getTypedProfile(String ID)
			throws IOException, ProfileException, JAXBException {
		return toRuntimeProfile(profileToContent.get(ID));
	}

	@Override
	public void persistProfile(String ID, HL7V2XConformanceProfile profile)
			throws IOException, JAXBException {
		profileToContent.put(ID, toString(profile));
	}

	@Override
	public List<String> getAllIds() {
		List<String> list = Lists.newArrayList();
		list.addAll(profileToContent.keySet());
		return list;
	}

	private HL7V2XConformanceProfile toRuntimeProfile(String string)
			throws JAXBException {
		return (HL7V2XConformanceProfile) unmarshaller
				.unmarshal(new StringReader(string));
	}

	private String toString(HL7V2XConformanceProfile p) throws JAXBException {
		StringWriter sw = new StringWriter();
		marshaller.marshal(p, sw);
		return sw.toString();

	}
}