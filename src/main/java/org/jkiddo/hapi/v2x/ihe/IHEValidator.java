package org.jkiddo.hapi.v2x.ihe;

import java.io.IOException;
import java.util.List;

import net.ihe.gazelle.hl7.validator.core.GazelleHL7Assertion;
import net.ihe.gazelle.hl7.validator.core.GazelleValidator;
import net.ihe.gazelle.hl7.validator.core.ResourceStoreFactory;
import net.ihe.gazelle.hl7.validator.report.Error;
import net.ihe.gazelle.hl7.validator.report.GazelleProfileException;
import net.ihe.gazelle.hl7.validator.report.HL7v2ValidationReport;
import net.ihe.gazelle.hl7.validator.report.ValidationResults;
import net.ihe.gazelle.hl7.validator.report.Warning;
import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.conf.ProfileException;
import ca.uhn.hl7v2.conf.parser.ProfileParser;
import ca.uhn.hl7v2.conf.spec.RuntimeProfile;
import ca.uhn.hl7v2.conf.store.ProfileStore;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.util.Terser;
import ca.uhn.hl7v2.validation.DefaultValidator;
import ca.uhn.hl7v2.validation.ValidationException;
import ca.uhn.hl7v2.validation.ValidationExceptionHandler;
import ca.uhn.hl7v2.validation.Validator;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

public class IHEValidator<T> extends DefaultValidator<T> implements
		Validator<T> {

	private ProfileStore profileStore;
	private ProfileParser profileParser = new ProfileParser(true);

	public IHEValidator(HapiContext context) {
		super(context);
		this.profileStore = context.getProfileStore();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T validate(Message incomingMessage,
			ValidationExceptionHandler<T> handler) throws HL7Exception {

		String profileIdentifier = new Terser(incomingMessage).get("/MSH-21");

		// Don't know if this is needed
		Message returnMessage = (Message) super.validate(incomingMessage,
				handler);

		if (Strings.isNullOrEmpty(profileIdentifier))
			return (T) returnMessage;
		else {

			RuntimeProfile runtimeProfile;
			try {
				runtimeProfile = getRuntimeProfile(profileIdentifier);
			} catch (IOException | ProfileException e) {
				throw new HL7Exception(
						"Profile could not be identified by ID: "
								+ profileIdentifier, e);
			}

			HL7v2ValidationReport validationReport = new HL7v2ValidationReport();
			GazelleValidator gv = new GazelleValidator(
					new ResourceStoreFactory(), validationReport.getResults());
			gv.validate(incomingMessage, runtimeProfile.getMessage(),
					runtimeProfile.getHL7Version());

			if (validationReport.getResults().getErrorCounter() > 0) {
				try {
					returnMessage = generateResponseMessage(incomingMessage,
							returnMessage, validationReport.getResults(),
							handler);
				} catch (IOException e) {
					throw new RuntimeException(
							"Generation of response message failed", e);
				}
			}
		}
		return (T) returnMessage;
	}

	private Message generateResponseMessage(Message incoming,
			Message returnMessage, ValidationResults validationResults,
			ValidationExceptionHandler<T> handler) throws HL7Exception,
			IOException {

		// Don't no which exceptions are the right ones besides Error
		ImmutableList<Object> listOfErrors = FluentIterable
				.from(validationResults.getNotifications())
				.filter(new Predicate<Object>() {

					@Override
					public boolean apply(Object arg0) {
						return arg0.getClass() != GazelleHL7Assertion.class;
					}
				}).filter(new Predicate<Object>() {

					@Override
					public boolean apply(Object arg0) {
						return arg0.getClass() != GazelleProfileException.class;
					}
				}).filter(new Predicate<Object>() {

					@Override
					public boolean apply(Object arg0) {
						return arg0.getClass() != Warning.class;
					}
				}).toImmutableList();

		Message nackMessage = incoming.generateACK(AcknowledgmentCode.AR, null);
		List<ValidationException> exceptions = Lists.newArrayList();

		for (int i = 0; i < listOfErrors.size(); i++) {
			Object possibleError = listOfErrors.get(i);
			if (possibleError.getClass() == Error.class) {
				Error error = (Error) possibleError;
				error.getHl7Exception().populateResponse(nackMessage,
						AcknowledgmentCode.AR, i);
				exceptions.add(ValidationException.fromHL7Exception(error
						.getHl7Exception()));
			}
		}
		ValidationException[] a = exceptions
				.toArray(new ValidationException[0]);
		handler.onExceptions(a);
		return nackMessage;
	}

	private RuntimeProfile getRuntimeProfile(String profileIdentifier)
			throws HL7Exception, IOException, ProfileException {
		String profileAsText = profileStore.getProfile(profileIdentifier);
		return profileParser.parse(profileAsText);
	}
}
