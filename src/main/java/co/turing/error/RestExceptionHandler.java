package co.turing.error;

import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;

import java.lang.reflect.Field;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    String prefixService;

    @Autowired
    private Environment environment;

    /**
     * Handles ApiException.
     *
     * @param ex the ApiException
     * @return the ApiError object
     */
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Object> handleGenericException(
            ApiException ex) {
        log.info("Received exception :: " + ex.getErrorCode());
        ApiError apiError = new ApiError(ex.getHttpStatus());
        apiError.setCode(ex.getErrorCode());
        apiError.setMessage(ex.getMessage());
        apiError.setField(ex.getField());
        ex.printStackTrace();
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthException(
            AuthenticationException ex) {
        log.info("Received Auth exception :: ");
        ApiError response = new ApiError(HttpStatus.UNAUTHORIZED);
        response.setMessage(TuringErrors.AUTH_FAILED.getMessage());
        response.setCode(TuringErrors.AUTH_FAILED.getCode());
        response.setField("No Auth");
        ex.printStackTrace();
        return buildResponseEntity(response);
    }

    @ExceptionHandler(StripeException.class)
    protected ResponseEntity<Object> handleStripeException(
            StripeException ex) {
        log.info("Received Auth exception :: ");
        ApiError response = new ApiError(HttpStatus.valueOf(ex.getStatusCode()));
        response.setMessage(ex.getMessage());
        response.setCode(ex.getCode());
        response.setField("source");
        ex.printStackTrace();
        return buildResponseEntity(response);
    }


    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        ex.printStackTrace();
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }


    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        ex.printStackTrace();
        return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.info("Received exception :: Argument");
        ApiError apiError = new ApiError(BAD_REQUEST);
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (fieldError.getField().equals("email")) {
                apiError.setCode(TuringErrors.INVALID_EMAIL.getCode());
                apiError.setMessage(TuringErrors.INVALID_EMAIL.getMessage());
            } else if (fieldError.getField().equals("phone")) {
                apiError.setCode(TuringErrors.INVALID_PHONE.getCode());
                apiError.setMessage(TuringErrors.INVALID_PHONE.getMessage());
            } else if (fieldError.getField().equals("phone")) {
                apiError.setCode(TuringErrors.INVALID_PHONE.getCode());
                apiError.setMessage(TuringErrors.INVALID_PHONE.getMessage());
            } else if (fieldError.getField().equals("creditCard")) {
                apiError.setCode(TuringErrors.INVALID_CARD.getCode());
                apiError.setMessage(TuringErrors.INVALID_CARD.getMessage());
            }
            apiError.setMessage(fieldError.getDefaultMessage());
            apiError.setField(fieldError.getField());
        }
        ex.printStackTrace();
        return buildResponseEntity(apiError);
    }

    private String getPrefixedErrorCode(String code) {
        return prefixService.toUpperCase() + "-" + code;
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            javax.validation.ConstraintViolationException ex) {
        log.info("Received exception :: Constraint");
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setCode(TuringErrors.FIELD_REQUIRED.getCode());

        String message = "";

        for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
            String field = ((PathImpl) cv.getPropertyPath()).getLeafNode().asString();
            message = message + field + " ";
        }
        apiError.setMessage(message + "are required");
        ex.printStackTrace();
        return buildResponseEntity(apiError);
    }


    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String error = "Malformed JSON request";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex);
        apiError.setCode(TuringErrors.UNEXPECTED_EXCEPTION.getCode());
        ex.printStackTrace();
        return buildResponseEntity(apiError);
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Error writing JSON output";
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex);
        apiError.setCode(TuringErrors.UNEXPECTED_EXCEPTION.getCode());
        ex.printStackTrace();
        return buildResponseEntity(apiError);
    }


    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        ex.printStackTrace();
        return buildResponseEntity(apiError);
    }

    /**
     * Handles Exception.
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(
            Exception ex) {
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
        apiError.setCode(TuringErrors.UNEXPECTED_EXCEPTION.getCode());
        apiError.setMessage(ex.getMessage());
        ex.printStackTrace();
        return buildResponseEntity(apiError);
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return ResponseEntity.status(Integer.parseInt(apiError.getStatus())).body(new ErrorResponse(apiError));
    }

}