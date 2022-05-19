package com.ifeb2.scdevsecurity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MethodEnums {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE");

    private final String type;

    public static MethodEnums get(String type) {
        for (MethodEnums value : MethodEnums.values()) {
            if (type.equals(value.getType())) {
                return value;
            }
        }
        return GET;
    }
}
