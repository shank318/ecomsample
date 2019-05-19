package co.turing.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private String  errorCode;
    private String field;
    private HttpStatus httpStatus= HttpStatus.BAD_REQUEST;
    public ApiException(String message, String code) {
        super(message);
        this.errorCode = code;
    }

    public ApiException(String message, String code,HttpStatus httpStatus) {
        super(message);
        this.errorCode = code;
        this.httpStatus= httpStatus;
    }

    public ApiException(String message, String code, String field) {
        super(message);
        this.errorCode = code;
        this.field = field;
    }


    public ApiException(String message, String code, String field,HttpStatus httpStatus) {
        super(message);
        this.errorCode = code;
        this.field = field;
        this.httpStatus= httpStatus;
    }

}