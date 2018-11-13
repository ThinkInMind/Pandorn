package cn.d1m.pandora.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Objects;

@Slf4j
public class ApplicationContextHolder {

    private ApplicationContextHolder() {
        throw new IllegalStateException("Utils class");
    }

    private static ApplicationContext applicationContext;

    /**
     * Get application context from everywhere
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Get bean by class
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * Get bean by class name
     *
     * @param name
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static <T> Map<String, T> getBeanMaps(Class<T> tClass) {
        return getApplicationContext().getBeansOfType(tClass);
    }


    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (Objects.isNull(ApplicationContextHolder.applicationContext)) {
            ApplicationContextHolder.applicationContext = applicationContext;
        } else {
            log.warn("You can not set applicationContext!");
        }
    }
}
