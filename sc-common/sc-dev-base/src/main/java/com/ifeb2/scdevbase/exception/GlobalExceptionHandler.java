package com.ifeb2.scdevbase.exception;

import com.ifeb2.scdevbase.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(getStackTrace(e));
        return Result.error("服务器错误，请联系管理员");
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable e) {
        log.error(getStackTrace(e));
        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
    }

    /**
     * BadCredentialsException
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public Result badCredentialsException(BadCredentialsException e) {
        String message = "错误凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        log.error(message);
        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), message);
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result handleException(HttpRequestMethodNotSupportedException e) {
        log.error(getStackTrace(e));
        return Result.error(HttpStatus.NOT_FOUND.getReasonPhrase(), "不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result notFount(RuntimeException e) {
        log.error(getStackTrace(e));
        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "请求错误:" + e.getMessage());
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = RequestException.class)
    public Result RequestException(RequestException e) {
        log.error(getStackTrace(e));
        return Result.error(e.getStatus(), e.getMessage());
    }


    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(getStackTrace(e));
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if (msg.equals(message)) {
            message = str[1] + ":" + message;
        }
        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), message);
    }

    /**
     * 获取堆栈信息
     */
    private static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
