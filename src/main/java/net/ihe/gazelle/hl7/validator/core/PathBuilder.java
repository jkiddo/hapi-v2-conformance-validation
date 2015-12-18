package net.ihe.gazelle.hl7.validator.core;

import java.util.ArrayList;

public class PathBuilder extends ArrayList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String pathSeparator = "/";

	public PathBuilder() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (String string : this.toArray(new String[1])) {
			if (builder.length() > 0) {
				builder.append(pathSeparator);
			}
			builder.append(string);
		}
		return builder.toString();
	}

	public void removeLastItem() {
		if (this.size() > 0) {
			this.remove(this.size() - 1);
		}
	}

	public void replaceLastItem(String stringToAdd) {
		if (this.size() > 1) {
			this.set(this.size() - 1, stringToAdd);
		}
	}

	public void addRep(int rep) {
		String lastItem = this.get(this.size() - 1);
		if (lastItem.contains("[")) {
			lastItem = lastItem.replaceFirst("\\[[0-9]*\\]", "[" + rep + "]");
		} else {
			lastItem = lastItem.concat("[" + rep + "]");
		}
		replaceLastItem(lastItem);
	}
}
