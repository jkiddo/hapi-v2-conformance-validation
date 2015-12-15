package org.jkiddo.hapi.v2x.ihe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.conf.parser.ProfileParser;
import ca.uhn.hl7v2.conf.spec.RuntimeProfile;
import ca.uhn.hl7v2.conf.store.ProfileStore;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

public class IHEProfileFileStore implements ProfileStore {

	private final Logger logger = LoggerFactory
			.getLogger(IHEProfileFileStore.class);
	private final Map<String, String> profileToContent = new HashMap<String, String>();
	private final ProfileParser pp = new ProfileParser(true);

	public IHEProfileFileStore(File root) {
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
}