package co.turing.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
public class ErrorResponse {
    public ErrorResponse(ApiError error) {
        this.error = error;
    }

    ApiError error;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @AllArgsConstructor
    static class ApiValidationError {
        public String object;
        public String field;
        public Object rejectedValue;
        public String message;

        ApiValidationError(String object, String message) {
            this.object = object;
            this.message = message;
        }
    }
}
