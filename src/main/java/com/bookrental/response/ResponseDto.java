package com.bookrental.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private boolean success;
    private String message;
    private String errorCode;
    private Map<String, Object> data;


    public static ResponseDto success(Map<String, Object> data) {
        ResponseDto response = new ResponseDto();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static ResponseDto success() {
        ResponseDto response = new ResponseDto();
        response.setSuccess(true);
        return response;
    }

    public static ResponseDto error(String errorCode, Map<String, Object> errorDetails) {
        ResponseDto response = new ResponseDto();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setData(errorDetails);
        return response;
    }
}
