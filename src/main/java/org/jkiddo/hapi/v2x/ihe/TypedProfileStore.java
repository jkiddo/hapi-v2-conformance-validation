package org.jkiddo.hapi.v2x.ihe;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import net.ihe.gazelle.xsd.HL7V2XConformanceProfile;
import ca.uhn.hl7v2.conf.ProfileException;
import ca.uhn.hl7v2.conf.store.ProfileStore;

public interface TypedProfileStore extends ProfileStore {

	public HL7V2XConformanceProfile getTypedProfile(String ID)
			throws IOException, ProfileException, JAXBException;

	public void persistProfile(String ID, HL7V2XConformanceProfile profile)
			throws IOException, JAXBException;

	public List<String> getAllIds();

}
