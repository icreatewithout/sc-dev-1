package com.ifeb2.scdevfeign;

import lombok.Data;

import java.util.Map;

@Data
public class RequestHeaders {

    private String token;

    private Map<String, String> headers;

}
