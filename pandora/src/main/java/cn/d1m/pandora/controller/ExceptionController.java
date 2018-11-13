package cn.d1m.pandora.controller;

import cn.d1m.pandora.domain.exception.BusinessException;
import cn.d1m.pandora.domain.exception.NullTokenAuthenticationException;
import cn.d1m.pandora.domain.exception.WithVersionException;
import cn.d1m.pandora.domain.web.BaseResponse;
import cn.d1m.pandora.entry.output.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(value = {BusinessException.class, WithVersionException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object wechatBusinessExceptionHandle(RuntimeException w) {
        return CommonResponse.build("fail", 400, w.getMessage(), null);
    }

    @ExceptionHandler(NullTokenAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Object nullTokenAuthenticationException(NullTokenAuthenticationException w) {
        return BaseResponse.build(false, HttpStatus.UNAUTHORIZED.value(), null, null, w.getMessage(),null);
    }

    @ExceptionHandler(AccountExpiredException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Object nullTokenAuthenticationException(AccountExpiredException w) {
        return BaseResponse.build(false, HttpStatus.FORBIDDEN.value(), null, null, w.getMessage(),null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Object exceptionHandle(Exception e) {
        log.error("SYSTEM ERROR \n", e);
        return CommonResponse.builder().errorInfo("系统繁忙，请稍候再试！").resultCode(500).status("fail").build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Object httpMediaTypeNotSupportedExceptionHandle(HttpMediaTypeNotSupportedException e) {
        return CommonResponse.buildFailure(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Object httpMediaTypeNotSupportedExceptionHandle(HttpRequestMethodNotSupportedException e) {
        return CommonResponse.buildFailure(e.getMessage());
    }

    //bean校验异常
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleResourceNotFoundException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage()).append("\n");
        }
        return CommonResponse.buildFailure(strBuilder.toString());
    }

    //bean校验异常
    @ExceptionHandler(value = {BindException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Object handleResourceNotFoundException(BindException e) {
        final List<FieldError> fieldErrors = e.getFieldErrors();
        StringBuilder strBuilder = new StringBuilder();
        for (FieldError violation : fieldErrors) {
            strBuilder.append(violation.getDefaultMessage()).append("\n");
        }
        return CommonResponse.buildFailure(strBuilder.toString());
    }

    //方法校验异常
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object handle(MethodArgumentNotValidException exception) {
        return CommonResponse.build("fail", 400, exception.getBindingResult().getFieldErrors()
                .parallelStream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(",")), null);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return CommonResponse.build("fail", 400, e.getMessage(), null);
    }
//
//    @ExceptionHandler(value = WithVersionException.class)
//    @ResponseStatus(HttpStatus.OK)
//    public CommonResponse handleMissingServletRequestParameterException(WithVersionException e) {
//        return CommonResponse.build("fail", 400, e.getMessage(), null);
//    }


}
