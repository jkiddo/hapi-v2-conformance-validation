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
	
	private String messageProfileIdentifier;

	public IHERuleBinding(String theVersion, String theScope, String messageProfileIdentifier, IHEValidationRule theRule) {
		super(theVersion, theScope, theRule);
		this.messageProfileIdentifier = messageProfileIdentifier;
	}

	@Override
	public boolean appliesToVersion(String theVersion) {
		return doesRuleApply(getVersion(), theVersion);
	}

	@Override
	public boolean appliesToScope(String theType) {
		return doesRuleApply(getScope(), theType);
	}
	
	public boolean appliesToMessagProfileIdentifier(String profileId){
		if (profileId == null && messageProfileIdentifier == null){
			return true;
		} else if (STAR.equals(messageProfileIdentifier)){
			return true;
		} else if (profileId != null && profileId.contains(messageProfileIdentifier)){
			return true;
		} else {
			return false;
		}
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

	public String getMessageProfileIdentifier() {
		return messageProfileIdentifier;
	}
}
