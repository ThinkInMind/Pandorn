package cn.d1m.pandora.domain.exception;

/**
 * Created by jone.wang on 2018/10/23.
 * Description:
 */
public class WithVersionException extends RuntimeException {
    public WithVersionException(String message) {
        super(message);
    }
}
