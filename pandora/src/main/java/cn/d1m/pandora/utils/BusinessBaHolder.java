package cn.d1m.pandora.utils;

import cn.d1m.pandora.domain.entity.BusinessBa;
import cn.d1m.pandora.domain.exception.BusinessException;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by jone.wang on 2018/9/28.
 * Description:
 */
public class BusinessBaHolder {

    private static final ThreadLocal<BusinessBa> sessionThreadLocal = new ThreadLocal<>();

    public static void setBusinessBa(BusinessBa session) {
        sessionThreadLocal.set(session);
    }

    public static BusinessBa getBusinessBa() {
        return sessionThreadLocal.get();
    }

    public static <T> T getBusinessBaForApply(Function<BusinessBa, T> function) {
        final BusinessBa businessBa = sessionThreadLocal.get();
        if (Objects.isNull(businessBa)) {
            throw new BusinessException("请登录");
        }
        return function.apply(businessBa);
    }

    public static <T> T getIdForApply(Function<String, T> function) {
        final BusinessBa businessBa = sessionThreadLocal.get();
        if (Objects.isNull(businessBa)) {
            throw new BusinessException("请登录");
        }
        return function.apply(businessBa.getId());
    }
}
