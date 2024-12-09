package com.bookrental.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {

    @NotNull
    private boolean success;

    private String message;

    private Map<String, Object> data = Collections.emptyMap();

    private ErrorDetail error;

    private Instant timestamp = Instant.now();

    private HttpStatus httpStatus;

    @Getter
    @AllArgsConstructor
    @ToString
    public static class ErrorDetail {
        @NotNull
        private String code;

        @NotNull
        private String details;

        private Map<String, Object> additionalInfo;

        public ErrorDetail(String code, String details) {
            this.code = code;
            this.details = details;
            this.additionalInfo = Collections.emptyMap();
        }
    }


    public static ResponseDto success(Map<String, Object> data) {
        return ResponseDto.builder()
                .success(true)
                .message("Operation successful")
                .data(Optional.ofNullable(data).orElse(Collections.emptyMap()))
                .httpStatus(HttpStatus.OK)
                .build();
    }


    public static ResponseDto success() {
        return ResponseDto.builder()
                .success(true)
                .message("Operation successful")
                .httpStatus(HttpStatus.OK)
                .build();
    }


    public static ResponseDto error(String code, String details) {
        return error(code, details, Collections.emptyMap(), HttpStatus.BAD_REQUEST);
    }


    public static ResponseDto error(
            String code,
            String details,
            Map<String, Object> additionalInfo,
            HttpStatus httpStatus
    ) {
        return ResponseDto.builder()
                .success(false)
                .message("Operation failed")
                .error(new ErrorDetail(code, details, additionalInfo))
                .httpStatus(httpStatus)
                .build();
    }


    public static ResponseDto validationError(String details) {
        return error("VALIDATION_ERROR", details, Collections.emptyMap(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    public static ResponseDto notFound(String resourceName) {
        return error("NOT_FOUND", resourceName + " not found",
                Collections.emptyMap(),
                HttpStatus.NOT_FOUND
        );
    }


    public boolean isSuccessful() {
        return success;
    }
}
