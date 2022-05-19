package com.ifeb2.adminservice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> {

    private Integer code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private String message;

    public static Result success() {
        return Result.builder().message(HttpStatus.OK.getReasonPhrase()).code(HttpStatus.OK.value()).build();
    }

    public static Result success(Object data) {
        return Result.builder().message(HttpStatus.OK.getReasonPhrase()).code(HttpStatus.OK.value()).data(data).build();
    }

    public static Result success(Object data, String message) {
        return Result.builder().message(message).code(HttpStatus.OK.value()).data(data).build();
    }

    public static Result success(Integer code, Object data, String message) {
        return Result.builder().message(message).code(code).data(data).build();
    }

    public static Result error() {
        return Result.builder().message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
    }

    public static Result error(Object data) {
        return Result.builder().message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).data(data).build();
    }

    public static Result error(Object data, String message) {
        return Result.builder().message(message).code(HttpStatus.INTERNAL_SERVER_ERROR.value()).data(data).build();
    }

    public static Result error(Integer status, String message) {
        return Result.builder().message(message).code(status).build();
    }

    public static Result error(Integer code, Object data, String message) {
        return Result.builder().message(message).code(code).data(data).build();
    }


    public static String toJson(Result result) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(result);
        } catch (Exception e) {
            log.error("解析失败：{}", e.getMessage());
        }
        return "{}";
    }
}
