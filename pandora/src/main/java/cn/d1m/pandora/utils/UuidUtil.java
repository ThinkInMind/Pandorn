package cn.d1m.pandora.utils;

import java.util.UUID;

public class UuidUtil {

    public static String shortUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String UUID() {
        return UUID.randomUUID().toString();
    }
}
