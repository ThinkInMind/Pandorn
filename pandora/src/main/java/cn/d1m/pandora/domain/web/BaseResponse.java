package cn.d1m.pandora.domain.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@SuppressWarnings("unchecked")
public class BaseResponse<T> implements ErrorResponse {
    @ApiModelProperty(value = "是否成功", required = true, example = "true")
    private Boolean success;
    @ApiModelProperty(value = "状态码", required = true, example = "400")
    private Integer resultCode;
    @ApiModelProperty(value = "错误代码", example = "400001")
    private Integer errorCode;
    @ApiModelProperty(value = "返回信息", example = "操作成功")
    private String msg;
    @ApiModelProperty(value = "错误信息", example = "密码错误")
    private String errorMsg;
    @ApiModelProperty(value = "返回对象json")
    private T data;


    public static <E> BaseResponse<E> buildSuccessfully() {
        return buildSuccessfully(null);
    }



    public static <E> BaseResponse<E> buildSuccessfully(E resultContent) {
        return new BaseResponse(true, HttpStatus.OK.value(),null, "success", null, resultContent);
    }

    public static <E> BaseResponse<E> build(Boolean success, Integer resultCode, Integer errorCode, String msg, String errorMsg,  E data) {
        return new BaseResponse<>(success, resultCode,errorCode, msg, errorMsg, data);
    }

    public static BaseResponse buildSuccessfullyMsg(String msg) {
        return new BaseResponse(true, HttpStatus.OK.value(),null, msg, null, null);
    }

    public static <E> BaseResponse<E> buildFailure() {
        return buildFailure("fail");
    }

    public static <E> BaseResponse<E> buildFailure(String resultMessage) {
        return new BaseResponse(false, HttpStatus.BAD_REQUEST.value(),null, null, resultMessage, null);
    }

    public static <E> BaseResponse<E> buildFailure(String resultMessage, Integer resultCode ,Integer errorCode) {
        return new BaseResponse(false, resultCode,errorCode, null, resultMessage, null);
    }

    public static BaseResponseBuilder<?> ok() {
        final BaseResponseBuilder<?> builder = new BaseResponseBuilder<>();
        return builder.success(true).resultCode(HttpStatus.OK.value());
    }


    public static <E> BaseResponseBuilder<E> ok(E data) {
        final BaseResponseBuilder<E> builder = new BaseResponseBuilder<E>();
        return builder.success(true).resultCode(HttpStatus.OK.value()).data(data);
    }

    public static BaseResponseBuilder<?> fail(String errorMsg) {
        final BaseResponseBuilder<?> builder = new BaseResponseBuilder<>();
        return builder.resultCode(400).errorMsg(errorMsg);
    }

}
