package com.ifeb2.scdevbase.utils;

import java.util.UUID;

public class IDUtil {

    /**
     * 生成uuid
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 雪花算法id
     * @return
     */
    public static long swId() {
        SnowflakeIdWorker worker = SnowflakeIdWorker.getInstance();
        return worker.nextId();
    }

}
