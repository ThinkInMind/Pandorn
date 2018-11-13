package cn.d1m.pandora.domain.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(BindingResult result) {
        super(result.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
    }

}
