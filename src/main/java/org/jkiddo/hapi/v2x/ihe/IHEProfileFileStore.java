package org.jkiddo.hapi.v2x.ihe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FilenameUtils;
import org.openehealth.ipf.gazelle.validation.core.stub.AcknowledgmentType;
import org.openehealth.ipf.gazelle.validation.core.stub.HL7V2XConformanceProfile;
import org.openehealth.ipf.gazelle.validation.core.stub.HL7V2XStaticDef;
import org.openehealth.ipf.gazelle.validation.core.stub.MetaDataType;
import org.openehealth.ipf.gazelle.validation.core.stub.SegmentType;
import org.openehealth.ipf.gazelle.validation.core.stub.UseCaseElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.conf.ProfileException;
import ca.uhn.hl7v2.conf.parser.ProfileParser;
import ca.uhn.hl7v2.conf.spec.RuntimeProfile;

import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

public class IHEProfileFileStore implements TypedProfileStore {

	private final Logger logger = LoggerFactory
			.getLogger(IHEProfileFileStore.class);
	private final ProfileParser pp = new ProfileParser(true);
	private final Cache<String, String> profileToContent = CacheBuilder.newBuilder().expireAfterAccess(20L, TimeUnit.MINUTES).build();

	private final JAXBContext jaxbContext = JAXBContext
			.newInstance(HL7V2XConformanceProfile.class, AcknowledgmentType.class, HL7V2XStaticDef.class, MetaDataType.class, SegmentType.class, UseCaseElementType.class);
	private final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	private final Marshaller marshaller = jaxbContext.createMarshaller();

	public IHEProfileFileStore() throws JAXBException {
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	}

	public IHEProfileFileStore(File root) throws JAXBException {

		this();

		logger.debug("Listing files from " + root.getAbsolutePath());
		File[] files = root.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				logger.debug("Found file: " + name);
				return name.endsWith("xml");
			}
		});

		for (File f : files) {
			FileInputStream fis = null;
			try {
				logger.debug("Trying to parse " + f.getName());
				fis = new FileInputStream(f);
				String content = CharStreams.toString(new InputStreamReader(
						fis, Charsets.UTF_8));

				RuntimeProfile profile = pp.parse(content);
				String profileName = FilenameUtils.removeExtension(f.getName());
				profileToContent.put(profileName, content);
				logger.debug(profileName + " succesfully parsed");
			} catch (Exception e) {
				logger.debug("File " + f.getName() + " could not be parsed");
			} finally {
				try {
					Closeables.closeQuietly(fis);
				} catch (Exception e) {
					logger.debug(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public String getProfile(String ID) throws IOException {
		return profileToContent.getIfPresent(ID);
	}

	@Override
	public void persistProfile(String ID, String profile) throws IOException {
		profileToContent.put(ID, profile);
	}

	@Override
	public HL7V2XConformanceProfile getTypedProfile(String ID)
			throws IOException, ProfileException, JAXBException {
		return toRuntimeProfile(getProfile(ID));
	}

	@Override
	public void persistProfile(String ID, HL7V2XConformanceProfile profile)
			throws IOException, JAXBException {
		persistProfile(ID, toString(profile));
	}

	@Override
	public List<String> getAllIds() {
		List<String> list = Lists.newArrayList();
		list.addAll(profileToContent.asMap().keySet());
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