package hapi.validation.hacker;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Tester {

	public static void main(String[] args) throws Exception {
		final Server server = new Server(9090);
		server.setHandler(new WebAppContext(
				"target/hapi.validation.hacker-0.0.1-SNAPSHOT.war", "/"));
		server.start();
	}

}
