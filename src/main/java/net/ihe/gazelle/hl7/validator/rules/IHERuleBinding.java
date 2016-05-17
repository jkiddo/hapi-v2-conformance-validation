package net.ihe.gazelle.hl7.validator.rules;

import ca.uhn.hl7v2.validation.impl.RuleBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IHERuleBinding extends RuleBinding<IHEValidationRule> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4297062539824032676L;

	private static Logger log = LoggerFactory.getLogger(IHERuleBinding.class);

	private static final String STAR = "*";
	
	public IHERuleBinding(String theVersion, String theScope, IHEValidationRule theRule) {
		super(theVersion, theScope, theRule);
	}

	@Override
	public boolean appliesToVersion(String theVersion) {
		return doesRuleApply(getVersion(), theVersion);
	}

	@Override
	public boolean appliesToScope(String theType) {
		return doesRuleApply(getScope(), theType);
	}
	
	protected static boolean doesRuleApply(String theBindingData, String theItemData) {
		boolean applies = false;
		if ((theBindingData.charAt(0) == '*') && theItemData.endsWith(theBindingData.replace("*", ""))) {
			applies = true;
		} else if ((theBindingData.charAt(theBindingData.length() - 1) == '*')
				&& theItemData.startsWith(theBindingData.replace("*", ""))) {
			applies = true;
		} else if (theBindingData.equals(theItemData) || theBindingData.equals("*")) {
			applies = true;
		}
		return applies;
	}

}
