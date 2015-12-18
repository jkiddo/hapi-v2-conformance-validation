package org.jkiddo.hapi.v2x.ihe;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.openehealth.ipf.gazelle.validation.core.stub.HL7V2XConformanceProfile;

import ca.uhn.hl7v2.conf.ProfileException;
import ca.uhn.hl7v2.conf.spec.RuntimeProfile;
import ca.uhn.hl7v2.conf.store.ProfileStore;

public interface TypedProfileStore extends ProfileStore {

	public HL7V2XConformanceProfile getTypedProfile(String ID)
			throws IOException, ProfileException, JAXBException;

	public void persistProfile(String ID, HL7V2XConformanceProfile profile)
			throws IOException, JAXBException;

	public List<String> getAllIds();

	public RuntimeProfile getRuntimeProfile(String profileIdentifier) throws ProfileException, IOException;

}
