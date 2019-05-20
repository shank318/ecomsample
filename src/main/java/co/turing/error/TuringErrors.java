package co.turing.error;

import lombok.Getter;

@Getter
public enum TuringErrors {

    UNEXPECTED_EXCEPTION("USR-1002", "Unexpected error occurred at server side", "Unknown"),
    USER_ALREADY_EXISTS("USR_04", "The email already exists", "email"),
    USER_PWD_INCORRECT("USR_01", "Email or Password is invalid.", "password"),
    FIELD_REQUIRED("USR_02", "The field(s) are/is required", ""),
    INVALID_EMAIL("USR_03", "The email is invalid", "email"),
    EMAIL_NOTEXIST("USR_05", "The email doesn't exist.", "email"),
    INVALID_CARD("USR_08", "this is an invalid Credit Card.", "credit_card"),
    INVALID_PHONE("USR_06", "this is an invalid phone number", "phone"),
    TOLONG("USR_07", "", ""),
    SHIPPING_ID_NOT_NUMBER("USR_09", "The Shipping Region ID is not number", "shipping_region_id"),
    AUTH_EMPTY("AUT_01", "Authorization code is empty", "No auth"),
    AUTH_FAILED("AUT_02", "Access Unauthorized", "No auth"),
    DEP_NOT_FOUND("DEP_02", "Don'exist department with this ID.", "department_id"),
    CAT_NOT_FOUND("CAT_01", "Don't exist category with this ID.", "category_id"),
    CART_ITEM_NOT_FOUND("CART_01", "Don't exist cart item with this ID.", "item_id"),
    PAG_02("PAG_02", "The field of order is not allow sorting.", "order"),
    PAG_01("PAG_01", "The order is not matched 'field,(DESC|ASC)", "order");


    private String code;
    private String message;
    private String field;

    TuringErrors(String code, String message, String field) {
        this.code = code;
        this.message = message;
        this.field = field;
    }


}
