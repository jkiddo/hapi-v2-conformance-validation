package org.jkiddo.web;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.jkiddo.hapi.v2x.ihe.TypedProfileStore;
import org.openehealth.ipf.gazelle.validation.core.stub.HL7V2XConformanceProfile;

import ca.uhn.hl7v2.conf.ProfileException;

@Path("/conformance")
@Produces({ MediaType.APPLICATION_JSON })
@Singleton
public class ValidationServlet {

	private final TypedProfileStore profileStore;

	@Inject
	public ValidationServlet(TypedProfileStore ps) {
		profileStore = ps;
	}

	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	public List<String> getAllCurrentProfileIds() {
		return profileStore.getAllIds();
	}

	@POST
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_XML })
	public String addConformanceSpec(final HL7V2XConformanceProfile cs)
			throws IOException, JAXBException {
		return addConformanceSpec(cs.getIdentiifer(), cs);
	}

	@POST
	@Path("{profileId}")
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_XML })
	public String addConformanceSpec(
			@PathParam("profileId") final String profileId,
			final HL7V2XConformanceProfile cs) throws IOException,
			JAXBException {
		profileStore.persistProfile(profileId, cs);
		return "Great - thanks for the profile (with ID: " + profileId + ")";
	}

	@GET
	@Path("{profileId}")
	@Produces({ MediaType.APPLICATION_XML })
	public HL7V2XConformanceProfile getExistingConformanceSpecification(
			@PathParam("profileId") final String profileId) throws IOException,
			ProfileException, JAXBException {
		return profileStore.getTypedProfile(profileId);
	}
}