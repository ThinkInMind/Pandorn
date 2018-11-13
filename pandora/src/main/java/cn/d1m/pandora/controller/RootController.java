package cn.d1m.pandora.controller;

import cn.d1m.pandora.domain.entity.BusinessBa;
import cn.d1m.pandora.domain.entity.BusinessPosBranch;
import cn.d1m.pandora.domain.web.BaseResponse;
import cn.d1m.pandora.service.BusinessBaService;
import cn.d1m.pandora.service.BusinessPosBranchTKService;
import cn.d1m.pandora.utils.BusinessBaHolder;
import cn.d1m.pandora.utils.CommonConstants;
import cn.d1m.pandora.utils.UuidUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by jone.wang on 2018/9/27.
 * Description:
 */
@RestController
@RequestMapping("/api")
public class RootController {

    @Autowired
    private BusinessBaService businessBaService;

    @Autowired
    private BusinessPosBranchTKService businessPosBranchTKService;

    @Autowired
    private RedisTemplate redisTemplate;

    @SuppressWarnings("unchecked")
    @PostMapping("/login")
    @ApiOperation(value = "Email登录", notes = "登录成功后保存返回的data信息呢，后面请求需要将data放在header里面（X-Session-Token:${data}）。")
    public BaseResponse<LoginResp> login(@Validated @RequestBody LoginReq loginReq) {

        final BusinessBa businessBa = businessBaService
                .findByEmailAndRawPassword(loginReq.getEmail(), loginReq.getPassword());
        final BusinessPosBranch branch = businessPosBranchTKService.selectByKey(businessBa.getBusinessPosBranchId());
        final LoginResp.LoginRespBuilder builder = LoginResp.builder();
        final String key = CommonConstants.TOKEN_PRE + UuidUtil.UUID();
        redisTemplate.opsForValue().set(key, businessBa, 30, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(businessBa.getId() + "::" + key, key, 30, TimeUnit.DAYS);
        builder.userToken(key);
        builder.email(businessBa.getEmail());
        builder.branchName(branch.getName());

        return BaseResponse.ok(builder.build()).msg("登录成功").build();
    }

    @Data
    @Builder
    private static class LoginResp {
        @ApiModelProperty(value = "用户访问令牌", required = true, example = CommonConstants.X_SESSION_TOKEN + "-239432lmf-324k230-3432k-234")
        @JsonProperty("X-Session-Token")
        private String userToken;
        @ApiModelProperty(value = "登录邮件账户", required = true, example = "d1m@ss.com")
        private String email;
        @ApiModelProperty(value = "名称", required = true, example = "中央大道店")
        private String branchName;
    }

    @Data
    private static class LoginReq {
        @NotBlank(message = "账号必填")
        @Email(message = "Email账户格式有误")
        @ApiModelProperty(value = "Email账户登录", example = "one@d1m.con")
        private String email;
        @NotBlank(message = "请输入密码")
        @ApiModelProperty(value = "密码", example = "11223311")
        private String password;
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/logout")
    @ApiOperation(value = "登出")
//    @CrossOrigin(value = "*", allowedHeaders = "*")
    public BaseResponse logout(@RequestHeader(CommonConstants.X_SESSION_TOKEN) String userToken) {

        return BusinessBaHolder.getIdForApply(id -> {
            redisTemplate.delete(userToken);
            redisTemplate.delete(id + "::" + userToken);
            return BaseResponse.ok().msg("登出成功").build();
        });
    }
}
