package com.ifeb2.scdevbase.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Data
public class RequestException extends RuntimeException{
    private Integer status = BAD_REQUEST.value();

    public RequestException(String msg){
        super(msg);
    }

    public RequestException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
}
