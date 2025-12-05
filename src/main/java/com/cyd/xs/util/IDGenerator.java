package com.cyd.xs.util;

import cn.hutool.core.util.IdUtil;

import java.util.concurrent.ThreadLocalRandom;

public class IDGenerator {
    public static Long generateId() {
        return System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(1000, 9999);
    }

    public static String generateContentId() {
        return "c" + System.currentTimeMillis();
    }

    public static String generateTopicId() {
        return "t" + System.currentTimeMillis();
    }

    public static String generateCircleId() {
        return "g" + System.currentTimeMillis();
    }
}
