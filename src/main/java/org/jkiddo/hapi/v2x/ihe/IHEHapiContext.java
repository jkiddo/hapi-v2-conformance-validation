package org.jkiddo.hapi.v2x.ihe;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.conf.store.ProfileStore;
import ca.uhn.hl7v2.validation.RespondingValidationExceptionHandler;
import ca.uhn.hl7v2.validation.Validator;

public class IHEHapiContext extends DefaultHapiContext {

	private final Validator<?> v;

	public IHEHapiContext(ProfileStore profileStore) {
		super();
		v = new IHEValidator<>(this);
		this.setValidationExceptionHandlerFactory(new RespondingValidationExceptionHandler(
				this));
		this.setProfileStore(profileStore);
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized <R> Validator<R> getMessageValidator() {
		return (Validator<R>) v;
	}
}
