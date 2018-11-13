package cn.d1m.pandora.entry.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse<T> {
    private String status;
    @JsonProperty("error_info")
    private String errorInfo;
    private Integer errorCode;
    private Integer resultCode;
    private String msg;
    private T data;

    public CommonResponse(String status) {
        this.status = status;
    }

    public CommonResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public CommonResponse(String status, String errorInfo) {
        this.status = status;
        this.errorInfo = errorInfo;
    }

    public CommonResponse(String status, T data, String errorInfo) {
        this.status = status;
        this.data = data;
        this.errorInfo = errorInfo;
    }

    public static CommonResponse buildSuccessfully() {
        return buildSuccessfully(null);
    }

    public static <E> CommonResponse<E> buildSuccessfully(E data) {
        return new CommonResponseBuilder<E>()
                .status("success")
                .resultCode(200)
                .msg("success")
                .data(data)
                .build();
    }

    public static <E> CommonResponse<E> build(String status, Integer resultCode, String msg, E data) {
        return new CommonResponseBuilder<E>()
                .status(status)
                .resultCode(resultCode)
                .msg(msg)
                .data(data)
                .build();
    }

    public static <E> CommonResponse<E> buildFailure() {
        return new CommonResponseBuilder<E>()
                .status("fail")
                .errorInfo("fail")
                .resultCode(400)
                .build();
    }

    public static <E> CommonResponse<E> buildFailure(String resultMessage) {
        return new CommonResponseBuilder<E>()
                .status("fail")
                .errorInfo(resultMessage)
                .resultCode(400)
                .build();
    }

    public static <E> CommonResponseBuilder<E> ok() {
        return new CommonResponseBuilder<E>().resultCode(200);
    }

    public static <E> CommonResponseBuilder<E> badRequest() {
        return new CommonResponseBuilder<E>().resultCode(400);
    }

    public static <E> CommonResponseBuilder<E> sysError() {
        return new CommonResponseBuilder<E>().resultCode(500);
    }

}
