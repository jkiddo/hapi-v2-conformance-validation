package hapi.validation.hacker;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import net.ihe.gazelle.hl7.validator.core.GazelleValidator;
import net.ihe.gazelle.hl7.validator.core.ResourceStoreFactory;
import net.ihe.gazelle.hl7.validator.report.HL7v2ValidationReport;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jkiddo.web.ContextListener;

import com.google.inject.servlet.GuiceFilter;

public class InMemoryTester {
	public static void main(String[] args) throws Exception {
		// The lame example

		HL7v2ValidationReport validationReport = new HL7v2ValidationReport();
		GazelleValidator gv = new GazelleValidator(
				new ResourceStoreFactory(), validationReport.getResults());
		
		final Server server = new Server(9090);
		final ServletContextHandler sch = new ServletContextHandler(server, "/");
		sch.addEventListener(new ContextListener());
		sch.addFilter(GuiceFilter.class, "/*",
				EnumSet.of(DispatcherType.REQUEST));
		sch.addServlet(DefaultServlet.class, "/");
		server.start();
	}
}
