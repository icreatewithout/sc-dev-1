package com.ifeb2.scdevbase.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RecurseUtil {

    public static <E> List<E> parents(List<E> list) {
        return parents(list, "id", "parentId", "children");
    }

    public static <E> List<E> parents(List<E> list, String key1, String key2, String key3) {

        List<E> root = new ArrayList<>();

        for (E menu : list) {
            Object parentId = getVal(key2, menu, menu.getClass());
            if (null == parentId || Long.valueOf(parentId.toString()).compareTo(0L) == 0) {
                root.add(menu);
            }
        }

        for (Object menu : root) {
            Object oid = getVal(key1, menu, menu.getClass());
            List<E> children = children(Long.valueOf(oid.toString()), list, key1, key2, key3);
            setVal(key3, menu, children);
        }

        return root;
    }

    private static <E> List<E> children(Long id, List<E> list, String key1, String key2, String key3) {
        List<E> childList = new ArrayList<>();

        for (E menu : list) {
            Object val = getVal(key2, menu, menu.getClass());
            if (null != val && Long.valueOf(val.toString()).compareTo(id) == 0) {
                childList.add(menu);
            }
        }

        for (E menu : childList) {
            Object oid = getVal(key1, menu, menu.getClass());
            setVal(key3, menu, children(Long.valueOf(oid.toString()), list, key1, key2, key3));
        }

        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }

    private static Object getVal(String name, Object obj, Class clazz) {
        Field field = ReflectionUtils.findField(clazz, name);
        if (null != field) {
            field.setAccessible(true);
            return ReflectionUtils.getField(field, obj);
        }
        return null;
    }

    private static void setVal(String name, Object obj, Object val) {
        Field field = ReflectionUtils.findField(obj.getClass(), name);
        if (null != field) {
            field.setAccessible(true);
            ReflectionUtils.setField(field, obj, val);
        }
    }

}
