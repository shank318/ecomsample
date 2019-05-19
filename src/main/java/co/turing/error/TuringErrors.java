package co.turing.error;

import lombok.Getter;

@Getter
public enum TuringErrors {

	UNEXPECTED_EXCEPTION("USR-1002", "Unexpected error occurred at server side"),
	USER_ALREADY_EXISTS("USR_04","The email already exists"),
	USER_PWD_INCORRECT("USR_01","Email or Password is invalid."),
	FIELD_REQUIRED("USR_02","The field(s) are/is required"),
    INVALID_EMAIL("USR_03","The email is invalid"),
    EMAIL_NOTEXIST("USR_05","The email doesn't exist."),
    INVALID_PHONE("USR_06","this is an invalid phone number"),
    TOLONG("USR_07",""),
    INVALID_CREDIT("USR_08","this is an invalid Credit Card."),
    SHIPPING_ID_NOT_NUMBER("USR_09","The Shipping Region ID is not number"),
	AUTH_EMPTY("AUT_01","Authorization code is empty"),
    AUTH_FAILED("AUT_02","Access Unauthorized"),
	DEP_NOT_FOUND("DEP_02","Don'exist department with this ID."),
	CAT_NOT_FOUND("CAT_01","Don't exist category with this ID."),
	PAG_02("PAG_02","The field of order is not allow sorting."),
	PAG_01("PAG_01","The order is not matched 'field,(DESC|ASC)");


	private String code;
	private String message;

	 TuringErrors(String code, String message) {
		this.code = code;
		this.message = message;
	}




}
