package hapi.validation.hacker;

import java.io.File;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jkiddo.hapi.v2x.ihe.IHEHapiContext;
import org.jkiddo.hapi.v2x.ihe.IHEProfileFileStore;
import org.jkiddo.web.ContextListener;

import ca.uhn.hl7v2.app.SimpleServer;

import com.google.inject.servlet.GuiceFilter;

public class Tests {
	public static void main(String[] args) throws Exception {
		// The lame example

		File root = new File(".");
		IHEProfileFileStore ps = new IHEProfileFileStore(root);
		IHEHapiContext hapiContext = new IHEHapiContext(ps);
		SimpleServer ss = new SimpleServer(hapiContext, 2575, false);
		ss.start();

		final Server server = new Server(9090);
		final ServletContextHandler sch = new ServletContextHandler(server, "/");
		sch.addEventListener(new ContextListener());
		sch.addFilter(GuiceFilter.class, "/*",
				EnumSet.of(DispatcherType.REQUEST));
		sch.addServlet(DefaultServlet.class, "/");
		server.start();
	}
}
