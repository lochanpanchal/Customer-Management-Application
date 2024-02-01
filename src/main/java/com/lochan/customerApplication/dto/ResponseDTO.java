package com.lochan.customerApplication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    private int status;
    private String message;
    private Object body;

    public ResponseDTO(HttpStatus status, String message, Object body){
        this.status = status.value();
        this.message = message;
        this.body = body;
    }
}
