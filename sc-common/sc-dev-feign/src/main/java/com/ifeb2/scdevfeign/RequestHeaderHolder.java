package com.ifeb2.scdevfeign;

public class RequestHeaderHolder {

    public static ThreadLocal<RequestHeaders> context = new ThreadLocal<>();

    public static RequestHeaders get() {
        return context.get();
    }

    public static String getToken() {
        RequestHeaders requestHeaders = context.get();
        return requestHeaders == null ? null : requestHeaders.getToken();
    }

    public static void set(RequestHeaders header) {
        context.set(header);
    }

    public static void shutdown() {
        context.remove();
    }

}
