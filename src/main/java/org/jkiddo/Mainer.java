package org.jkiddo;

import java.io.File;

import org.jkiddo.hapi.v2x.ihe.IHEHapiContext;
import org.jkiddo.hapi.v2x.ihe.IHEProfileFileStore;

import ca.uhn.hl7v2.app.SimpleServer;

public class Mainer {
	public static void main(String[] args) {

		// The lame example

		File root = new File(".");
		IHEHapiContext hapiContext = new IHEHapiContext(
				new IHEProfileFileStore(root));
		SimpleServer ss = new SimpleServer(hapiContext, 2575, false);
		ss.start();
	}
}
