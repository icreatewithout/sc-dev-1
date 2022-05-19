package com.ifeb2.scdevbase.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public static <T> Object getVal(T t, String name) {
        Field field = ReflectionUtils.findField(t.getClass(), name);
        if (null != field) {
            field.setAccessible(true);
            return ReflectionUtils.getField(field, t);
        }
        return null;
    }

    private static <T> void setVal(String name, T t, Object val) {
        Field field = ReflectionUtils.findField(t.getClass(), name);
        if (null != field) {
            field.setAccessible(true);
            ReflectionUtils.setField(field, t, val);
        }
    }
}
