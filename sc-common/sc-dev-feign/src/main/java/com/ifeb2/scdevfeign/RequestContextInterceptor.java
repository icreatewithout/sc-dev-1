package com.ifeb2.scdevfeign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
public class RequestContextInterceptor implements HandlerInterceptor {

    private final static String AUTH_HEADER = HttpHeaders.AUTHORIZATION.toLowerCase(Locale.ROOT);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        RequestHeaders headers = httpRequestToMap(request);
        RequestHeaderHolder.set(headers);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        RequestHeaderHolder.shutdown();
    }

    private RequestHeaders httpRequestToMap(HttpServletRequest request) {
        RequestHeaders headers = new RequestHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> map = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            if (AUTH_HEADER.equals(header)) {
                headers.setToken(request.getHeader(header));
            }
            map.put(header, request.getHeader(header));
        }
        headers.setHeaders(map);
        return headers;
    }
}
