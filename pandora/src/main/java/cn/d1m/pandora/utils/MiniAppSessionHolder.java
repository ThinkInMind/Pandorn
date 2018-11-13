package cn.d1m.pandora.utils;

import cn.d1m.pandora.domain.exception.BusinessException;
import cn.d1m.pandora.domain.wechat.MiniAppSession;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by jone.wang on 2018/9/18.
 * Description: 登录用户信息通过拦截器添加到ThreadLocal线程变了中，方便获取用户上下文信息
 */
public class MiniAppSessionHolder {
    private static final ThreadLocal<MiniAppSession> sessionThreadLocal = new ThreadLocal<>();

    public static void setMiniAppSession(MiniAppSession session) {
        sessionThreadLocal.set(session);
    }

    public static MiniAppSession getMiniAppSession() {
        return sessionThreadLocal.get();
    }

    public static <T> T getIdForApply(Function<String, T> function) {
        final MiniAppSession miniAppSession = sessionThreadLocal.get();
        if (Objects.isNull(miniAppSession)) {
            throw new BusinessException("请登录");
        }
        return function.apply(miniAppSession.getId());
    }
}
