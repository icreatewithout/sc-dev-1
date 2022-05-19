package com.ifeb2.gwservice.handller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class Response {

    private Integer code = HttpStatus.BAD_REQUEST.value();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "ZH",timezone = "GMT+8")
    private Date timestamp;
    private String message;

    private Response() {
        timestamp = new Date();
    }

    public static Response error(String message) {
        Response errorMessage = new Response();
        errorMessage.setMessage(message);
        return errorMessage;
    }

    public static Response error(Integer status, String message) {
        Response errorMessage = new Response();
        errorMessage.setCode(status);
        errorMessage.setMessage(message);
        return errorMessage;
    }

    public static byte[] toByte(Response message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
