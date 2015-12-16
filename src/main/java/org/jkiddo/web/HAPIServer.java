package org.jkiddo.web;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jkiddo.hapi.v2x.ihe.IHEHapiContext;
import org.jkiddo.hapi.v2x.ihe.IHEProfileFileStore;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.app.SimpleServer;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;

@Singleton
public class HAPIServer {

	private final SimpleServer ss;

	@Inject
	public HAPIServer(IHEProfileFileStore ps) {
		IHEHapiContext hapiContext = new IHEHapiContext(ps);
		ss = new SimpleServer(hapiContext, 2575, false);
		ss.registerApplication(new ReceivingApplication() {

			@Override
			public Message processMessage(Message theMessage,
					Map<String, Object> theMetadata)
					throws ReceivingApplicationException, HL7Exception {
				try {
					return theMessage.generateACK();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public boolean canProcess(Message theMessage) {
				return true;
			}
		});
		ss.start();
	}

	public void close() {
		ss.stop();
	}
}
