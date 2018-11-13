package cn.d1m.pandora.domain.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by jone.wang on 2018/9/28.
 * Description:
 */
public class NullTokenAuthenticationException extends AuthenticationException {
    public NullTokenAuthenticationException(String msg) {
        super(msg);
    }
}
