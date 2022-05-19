package com.ifeb2.gwservice.handller;

import lombok.Data;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Data
public class RequestException extends RuntimeException {
    private static final long serialVersionUID = -4542889129344765678L;

    private HttpStatus status = BAD_REQUEST;

    public RequestException(String msg) {
        super(msg);
    }

    public RequestException(HttpStatus status, String msg) {
        super(msg);
        this.status = status;
    }
}
