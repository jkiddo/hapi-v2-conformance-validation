package net.ihe.gazelle.hl7.validator.core;

public enum GazelleErrorCode {
	MESSAGE_ACCEPTED(0, "Message accepted"),
	SEGMENT_SEQUENCE_ERROR(100, "Segment sequence error"),
	REQUIRED_FIELD_MISSING(101, "Required field missing"),
	DATA_TYPE_ERROR(102, "Data type error"),
	TABLE_VALUE_NOT_FOUND(103, "Table value not found"),
	UNSUPPORTED_MESSAGE_TYPE(200, "Unsupported message type"),
	UNSUPPORTED_EVENT_CODE(201, "Unsupported event code"),
	UNSUPPORTED_PROCESSING_ID(202, "Unsupported processing id"),
	UNSUPPORTED_VERSION_ID(203, "Unsupported version id"),
	UNKNOWN_KEY_IDENTIFIER(204, "Unknown key identifier"),
	DUPLICATE_KEY_IDENTIFIER(205, "Duplicate key identifier"),
	APPLICATION_RECORD_LOCKED(206, "Application record locked"),
	APPLICATION_INTERNAL_ERROR(207, "Application internal error"),
	NOT_ALLOWED(1010, "Element not allowed"),
	CARDINALITY_ERROR(1012, "Cardinality mismatch"),
	LENGTH_ERROR(1013, "Length exceeded"),
	WRONG_CONSTANT_VALUE(1017, "Wrong constant value"),
	USAGE(1018, "Wrong usage"),
	FORMAT(1019, "Problem parsing message"),
	PRIMITIVE_RULE(1020, "Primitive rule not fulfilled"),
	UNKNOWN(1021, "Unknown error type"), 
	CONDITIONAL (1022, "Conditional"),
    WARNING(9001, "Warning");

	private static final String HL70357 = "HL70357";
	private final int code;
	private final String message;

	GazelleErrorCode(int errCode, String message) {
		this.code = errCode;
		this.message = message;
	}

	/**
	 * @return the integer error code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the error code message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the ErrorCode for the given integer
	 * 
	 * @param errCode
	 *            integer error code
	 * @return ErrorCode
	 */
	public static GazelleErrorCode errorCodeFor(int errCode) {
		for (GazelleErrorCode err : GazelleErrorCode.values()) {
			if (err.code == errCode) {
				return err;
			}
		}
		return UNKNOWN;
	}

	/**
	 * @return the HL7 table number
	 */
	public static String codeTable() {
		return HL70357;
	}
}
