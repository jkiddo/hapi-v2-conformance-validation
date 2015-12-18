package hapi.validation.hacker;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class BinaryTester {

	public static void main(String[] args) throws Exception {
		final Server server = new Server(9090);
		server.setHandler(new WebAppContext(
				"target/validation.war", "/"));
		server.start();
	}

}
