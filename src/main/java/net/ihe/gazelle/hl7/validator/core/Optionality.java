package net.ihe.gazelle.hl7.validator.core;

enum Optionality {
	REQUIRED("R"),
	REQUIRED_OR_EMPTY("RE"),
	OPTIONAL("O"),
	BACKWARD_COMPATIBILITY("B"),
	CONDITIONAL("C"),
	CONDITIONAL_OR_EMPTY("CE"),
	NOT_SUPPORTED("X");

	private String key;

	private Optionality(String key) {
		this.key = key;
	}

	public static Optionality getEnum(String key) {
		for (Optionality opt : values()) {
			if (opt.key.equals(key)) {
				return opt;
			} else {
				continue;
			}
		}
		return null;
	}

	public String getKey() {
		return key;
	}
}