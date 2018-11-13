package cn.d1m.pandora.utils;

import cn.d1m.pandora.domain.exception.BusinessException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * Created by jone.wang on 2018/9/27.
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class AssertUtils extends Assert {


    public static void notBlank(String object, String message) {
        if (StringUtils.isEmpty(object)) {
            throw new BusinessException(message);
        }
    }

    public static void isEmpty(Collection collection, String message) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(message);
        }
    }

    public static void notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new BusinessException(message);
        }
    }

    public static void isFalse(boolean b, String message) {
        if (b) {
            throw new BusinessException(message);
        }
    }

    public static void isTrue(boolean b, String message) {
        isFalse(!b, message);
    }
}
