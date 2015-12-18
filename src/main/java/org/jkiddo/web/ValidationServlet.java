package org.jkiddo.web;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.jkiddo.hapi.v2x.ihe.IHEHapiContext;
import org.jkiddo.hapi.v2x.ihe.TypedProfileStore;
import org.openehealth.ipf.gazelle.validation.core.stub.HL7V2XConformanceProfile;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.conf.ProfileException;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.validation.ValidationExceptionHandler;

@Path(ValidationServlet.NAME)
@Singleton
public class ValidationServlet {

	public final static String NAME = "/conformance";

	private final TypedProfileStore profileStore;
	private PipeParser pp;
	private IHEHapiContext context;

	@Inject
	public ValidationServlet(TypedProfileStore ps, IHEHapiContext context) throws HL7Exception {
		this.profileStore = ps;
		this.context = context;
		this.pp = new PipeParser();
	}

	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<String> getAllCurrentProfileIds() {
		return profileStore.getAllIds();
	}

	@POST
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_XML })
	public String addConformanceSpec(final HL7V2XConformanceProfile cs,
			@Context HttpServletRequest httpRequest) throws IOException,
			JAXBException {
		return addConformanceSpec(cs.getIdentiifer(), cs, httpRequest);
	}

	@POST
	@Path("{profileId}")
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_XML })
	public String addConformanceSpec(
			@PathParam("profileId") final String profileId,
			final HL7V2XConformanceProfile cs,
			@Context HttpServletRequest httpRequest) throws IOException,
			JAXBException {
		profileStore.persistProfile(profileId, cs);
		return "Great - thanks for the conformance profile (with ID: "
				+ profileId
				+ "). "
				+ "If you're using the online version of this service, you can now test whether your message conformns to the profile you just posted by making an HTTP POST to "
				+ NAME
				+ "/test containing the message that you wan't tested.\n\r"
				+ "If you  are using a local version of this service, you can also conduct the test by using your favourite hat's'n'pipes tool and send the message to "
				+ httpRequest.getServerName()
				+ ":2575, having the MSH-21 filled out with the ID: "
				+ profileId + "\n\r" + "Enjoy!";
	}

	@GET
	@Path("{profileId}")
	@Produces({ MediaType.APPLICATION_XML })
	public HL7V2XConformanceProfile getExistingConformanceSpecification(
			@PathParam("profileId") final String profileId) throws IOException,
			ProfileException, JAXBException {
		return profileStore.getTypedProfile(profileId);
	}

	@POST
	@Path("test")
	@Consumes({ MediaType.TEXT_PLAIN })
	@Produces({ MediaType.TEXT_PLAIN })
	public String doSend(String message) throws HL7Exception, LLPException,
			IOException {
		Message hl7Message = pp.parse(message);
		try {
			Object response = context.getMessageValidator().validate(hl7Message,
					(ValidationExceptionHandler<Object>) context
							.getValidationExceptionHandlerFactory()
							.getNewInstance(context));
			return response.toString();
		} catch (HL7Exception e) {
			return e.getMessage();
		}
	}
}