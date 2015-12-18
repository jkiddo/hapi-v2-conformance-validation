package net.ihe.gazelle.hl7.validator.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ihe.gazelle.hl7.validator.rules.IHERuleBinding;
import net.ihe.gazelle.hl7.validator.rules.IHEValidationRule;
import ca.uhn.hl7v2.validation.impl.ValidationContextImpl;

public class IHEValidationContext extends ValidationContextImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 7196272693343496546L;

	private static List<IHERuleBinding> myIHERuleBindings;

	public static final IHEValidationContext SINGLETON = new IHEValidationContext();

	private static Logger log = LoggerFactory.getLogger(IHEValidationContext.class);

	public IHEValidationContext() {
		super();
		listRules();
	}

	private static void listRules() {
		myIHERuleBindings = new ArrayList<IHERuleBinding>();

		
	}

	public List<IHEValidationRule> getIHEValidationRules(String profileOid, String messageProfileIdentifier,
			String hl7path) {
		hl7path = hl7path.replaceAll("\\[[0-9]*\\]", "");
		hl7path = hl7path.replaceAll("\\([^\\)]*[^/]*\\)", "");
		List<IHEValidationRule> active = new ArrayList<IHEValidationRule>();
		for (IHERuleBinding binding : myIHERuleBindings) {
			if (binding.getActive() && binding.appliesToVersion(profileOid) && binding.appliesToScope(hl7path)
					&& binding.appliesToMessagProfileIdentifier(messageProfileIdentifier)) {
				active.add(binding.getRule());
			}
		}
		return active;
	}

	public static List<IHERuleBinding> getIHERuleBindings() {
		return myIHERuleBindings;
	}
}
