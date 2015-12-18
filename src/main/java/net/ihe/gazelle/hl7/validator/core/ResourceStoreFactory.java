package net.ihe.gazelle.hl7.validator.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import ca.uhn.hl7v2.conf.store.CodeStore;

public class ResourceStoreFactory {

	private List<CodeStoreRegistration> codeStores;

	public ResourceStoreFactory() {
		this.codeStores = new ArrayList<CodeStoreRegistration>();
	}

	/**
	 * Registers a code store for use with all profiles.
	 */
	public void addCodeStore(CodeStore store, Integer weight) {
		addCodeStore(store, ".*", weight);
	}

	/**
	 * Registers a code store for use with a specific profile.
	 */
	public void addCodeStore(CodeStore store, String profileID, Integer weight) {
		Pattern pattern = Pattern.compile(profileID);
		addCodeStore(store, pattern, weight);
	}

	/**
	 * if no weight is provided for the code store, use 0 (store will be processed at this end)
	 * 
	 * @param store
	 * @param profileID
	 */
	public void addCodeStore(CodeStore store, String profileID) {
		addCodeStore(store, profileID, 0);
	}

	/**
	 * Registers a code store for use with certain profiles. The profiles with which the code store are used are determined by profileIdPattern, which is a regular expression that will be matched
	 * against profile IDs. For example suppose there are three profiles in the profile store, with the following IDs:
	 * <ol>
	 * <li>ADT:confsig-UHN-2.4-profile-AL-NE-Immediate</li>
	 * <li>ADT:confsig-CIHI-2.4-profile-AL-NE-Immediate</li>
	 * <li>ADT:confsig-CIHI-2.3-profile-AL-NE-Immediate</li>
	 * </ol>
	 * Then to use a code store with only the first profile, the profileIdPattern would be "ADT:confsig-UHN-2.4-profile-AL-NE-Immediate". To use a code store with both of the 2.4 profiles, the pattern
	 * would be ".*2\\.4.*". To use a code store with all profiles, the pattern would be '.*". Multiple stores can be registered for use with the same profile. If this happens, the first one that
	 * returned true for knowsCodes(codeSystem) will used. Stores are searched in the order of the decreasing weight they have been assigned.
	 */
	public void addCodeStore(CodeStore store, Pattern profileIdPattern, Integer weight) {
		codeStores.add(new CodeStoreRegistration(profileIdPattern, store, weight));
	}

	public CodeStore getCodeStore(String profileID, String tableID) {
		CodeStore codeStore = null;
		Collections.sort(codeStores);
		for (CodeStoreRegistration csr : codeStores) {
			if (csr.pattern.matcher(profileID).matches() && csr.store.knowsCodes(tableID)) {
				codeStore = csr.store;
				break;
			}
		}
		return codeStore;
	}

	protected class CodeStoreRegistration implements Comparable<CodeStoreRegistration> {
		private Pattern pattern;
		private CodeStore store;
		private Integer weight;

		public CodeStoreRegistration(Pattern p, CodeStore cs, Integer weight) {
			this.pattern = p;
			this.store = cs;
			if (weight != null) {
				this.weight = weight;
			} else {
				this.weight = 0;
			}
		}

		/**
		 * store CodeStoreRegistration by decreasing weight
		 */
		@Override
		public int compareTo(CodeStoreRegistration o) {
			if ((weight != null) && (o.weight != null)) {
				return (o.weight.compareTo(weight));
			} else {
				return 0;
			}
		}

		@Override
		public boolean equals(Object o){
			if (o == null){
				return false;
			}
			if (o instanceof CodeStoreRegistration){
				CodeStoreRegistration csr =  (CodeStoreRegistration)o;
				return getWeight().equals(csr.getWeight());
			}else{
				return false;
			}
		}

		public Pattern getPattern() {
			return pattern;
		}

		public void setPattern(Pattern pattern) {
			this.pattern = pattern;
		}

		public CodeStore getStore() {
			return store;
		}

		public void setStore(CodeStore store) {
			this.store = store;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		@Override public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((weight == null) ? 0 : weight);
			return result;
		}
	}

	public List<CodeStoreRegistration> getCodeStores() {
		return codeStores;
	}

	public void setCodeStores(List<CodeStoreRegistration> codeStores) {
		this.codeStores = codeStores;
	}

}
