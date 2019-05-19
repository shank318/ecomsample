package co.turing.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
//@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
//@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class ApiError {

    private String status;
    private String message;
    private String code;
    private String field;

    public ApiError(HttpStatus status) {
        this.status = String.valueOf(status.value());
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this.status = String.valueOf(status.value());
        this.message = "Unexpected error";
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this.status = String.valueOf(status.value());
        this.message = message;
    }



}

