package cn.d1m.pandora.domain.web;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by jone.wang on 2018/10/19.
 * Description:
 */
public interface ErrorResponse {
    @ApiModelProperty(value = "错误信息")
    String getErrorMsg();
}
