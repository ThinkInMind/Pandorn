package com.d1m.social.wechat.pandora.integration.layer.api.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@Slf4j
public class EnvUtils {

    private static final String ILLEGAL_MESSAGE = "You must init EnvUtils form environment!";

    private EnvUtils() {
        throw new UnsupportedOperationException("UnsupportedOperationException");
    }

    private static Environment environment;

    public static void setEnvironment(Environment environment) {
        EnvUtils.environment = environment;
    }

    public static String getProperty(String key) {
        if (environment == null) {
            throw new IllegalStateException(ILLEGAL_MESSAGE);
        } else {
            return environment.getProperty(key);
        }
    }

    public static Boolean getBoolean(String key) {
        if (environment == null) {
            throw new IllegalStateException(ILLEGAL_MESSAGE);
        } else {
            return environment.getProperty(key, Boolean.class);
        }
    }

    public static <T> T getProperty(String key, Class<T> tClass) {
        if (environment == null) {
            throw new IllegalStateException(ILLEGAL_MESSAGE);
        } else {
            return environment.getProperty(key, tClass);
        }
    }

    public static String getProperty(String key, String def) {
        if (environment == null) {
            throw new IllegalStateException(ILLEGAL_MESSAGE);
        }
        try {
            return environment.getProperty(key);
        } catch (Exception e) {
            log.error("get property error", e);
            return def;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProperty(String key, T def) {
        if (environment == null) {
            return def;
        }
        try {
            return environment.getProperty(key, (Class<T>) def.getClass());
        } catch (Exception e) {
            log.warn("get property error", e);
            return def;
        }
    }
}
