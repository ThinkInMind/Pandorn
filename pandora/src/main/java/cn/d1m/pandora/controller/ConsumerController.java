package cn.d1m.pandora.controller;


import cn.d1m.pandora.domain.dto.ConsumerCardDto;
import cn.d1m.pandora.domain.enumeration.ConsumerCardStatus;
import cn.d1m.pandora.domain.exception.BusinessException;
import cn.d1m.pandora.domain.web.BaseResponse;
import cn.d1m.pandora.domain.wechat.MiniAppSession;
import cn.d1m.pandora.entry.*;
import cn.d1m.pandora.entry.output.CommonResponse;
import cn.d1m.pandora.entry.output.scv.ConsumerFindResponse;
import cn.d1m.pandora.service.ConsumerCardService;
import cn.d1m.pandora.service.ConsumerCardTKService;
import cn.d1m.pandora.service.ConsumerService;
import cn.d1m.pandora.utils.AESUtils;
import cn.d1m.pandora.utils.CommonConstants;
import cn.d1m.pandora.utils.MiniAppSessionHolder;
import cn.d1m.pandora.utils.URLConstants;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/consumer/")
@Slf4j
public class ConsumerController {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    public ConsumerCardService consumerCardService;

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    private ConsumerCardTKService consumerCardTKService;

    //	@AutowiredgetloyaltyPoints
    @Autowired
    public ConsumerService consumerService;

    @RequestMapping(path = "/verify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public CommonResponse verifyConsumer(@Validated({First.class}) Consumer consumer, HttpServletRequest req) {

        String token = req.getAttribute("token").toString();//获取token
        return consumerService.verify(token);
    }

    /**
     * CRM query consumer API
     */
    @RequestMapping(path = "/query", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public CommonResponse query(@Validated({Query.class}) Consumer consumer,
                                HttpServletRequest req) {
        String encryptedData = req.getParameter("encryptedData");
        String iv = req.getParameter("iv");
        String token = req.getAttribute("token").toString();//获取token
        consumer.setId(token);
        consumer = this.getPhones(consumer, token, req.getHeader("token"), encryptedData, iv);
        return consumerService.queryConsumer(consumer);// 查询用户信息,
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public CommonResponse create(@Validated({Add.class}) Consumer consumer, HttpServletRequest req) {


        String token = req.getAttribute("token").toString();//获取token
        consumer.setId(token);// 设置到ID上面
        return consumerService.registAdd(consumer);// 新增用户
    }

    @RequestMapping(path = "/getConsumer", method = RequestMethod.POST)
    public CommonResponse getConsumer(HttpServletRequest req) {
        String token = req.getAttribute("token").toString();//获取token
//		consumer.setId(token);// 设置到ID上面
        return consumerService.getConsumer(token);//更新用户信息
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public CommonResponse update(@Validated({Update.class}) Consumer consumer, HttpServletRequest req) throws Exception {

        String token = req.getAttribute("token").toString();//获取token
        consumer.setId(token);// 设置到ID上面
        return consumerService.updateConsumer(consumer);//更新用户信息

    }

    /**
     * 登陆绑定用户
     *
     * @param
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public CommonResponse login(@Validated({Login.class}) Consumer consumer, HttpServletRequest req) {

        String token = req.getAttribute("token").toString();//获取token
//		consumer.setId(token);// 设置到ID上面
        return consumerService.login(consumer, consumer.getLoginID(), consumer.getPassword(), token);//更新用户信息
    }

    @RequestMapping(path = "/loginUpd", method = RequestMethod.POST)
    public CommonResponse loginUpd(@Validated({LoginUpd.class}) Consumer consumer, HttpServletRequest req) {

        String token = req.getAttribute("token").toString();//获取token
//		consumer.setId(token);// 设置到ID上面
        return consumerService.loginUpd(consumer, token);//更新用户信息
    }


    //获取礼券信息
    @GetMapping("coupons")
    @ApiOperation(value = "根据用户查询礼券")
    public BaseResponse<List<CouponsResp>> coupons() {

        MiniAppSession miniAppSession = MiniAppSessionHolder.getMiniAppSession();

        List<ConsumerCardDto> consumerCardDtos = consumerCardTKService.findCardDtoByParams(ImmutableMap.of("openid", miniAppSession.getOpenid()));
        //consumerCardTKService.findListConsumerCardByUser(token);
        if (CollectionUtils.isEmpty(consumerCardDtos)) {
            return BaseResponse.buildSuccessfullyMsg("没有数据");
        }
        List<CouponsResp> results = consumerCardDtos.stream()
                .map(d -> {
                    CouponsResp couponsResp = new CouponsResp();
                    BeanUtils.copyProperties(d.getCard(), couponsResp);
                    BeanUtils.copyProperties(d, couponsResp);
                    return couponsResp;
                }).collect(Collectors.toList());
        return BaseResponse.buildSuccessfully(results);
        // return consumerCardService.findvalidCardByToken(token);//更新用户信息
    }

    @Data
    private static class CouponsResp {
        @JsonFormat(pattern = "yyyy.MM.dd")
        @JsonProperty(value = "begin")
        private Timestamp beginTime;
        @JsonFormat(pattern = "yyyy.MM.dd")
        @JsonProperty(value = "end")
        private Timestamp endTime;
        private String id;
        private String title;
        private ConsumerCardStatus status;
        @JsonProperty(value = "pos_branch_name")
        private String name;
        @JsonFormat(pattern = "yyyy.MM.dd")
        @JsonProperty(value = "redeem_time")
        private Timestamp redeemTime;
        private String code;
    }


    @RequestMapping(path = "/getInvalidCoupons", method = RequestMethod.POST)
    public CommonResponse getInvalidCoupons(HttpServletRequest req) {

        String token = req.getAttribute("token").toString();//获取token
        return consumerCardService.findInvalidCardByToken(token);//更新用户信息
    }

    @ApiOperation(value = "查询会员等级和积分")
    @GetMapping(path = "/getloyaltyPoints")
    public CommonResponse<LoyalPointsResp> getloyaltyPoints() {
        final Consumer consumer = MiniAppSessionHolder.getIdForApply(id ->
                consumerService.getloyaltyPoints(id)
        );
        if (Objects.isNull(consumer)) {
            throw new BusinessException("找不到客户信息");
        }
        final LoyalPointsResp resp = new LoyalPointsResp();
        BeanUtils.copyProperties(consumer, resp);
        return CommonResponse.buildSuccessfully(resp);
    }

    @Data
    private static class LoyalPointsResp {
        @ApiModelProperty(value = "会员号", required = true, example = "12341")
        private String vipNumber;
        @ApiModelProperty(value = "会员等级", required = true, example = "222", allowableValues = "Normal,Silver,Rose,Gold,Diamond")
        private String loyaltyTier;
        @ApiModelProperty(value = "积分", required = true, example = "3000")
        private String loyaltyPoints;
        @ApiModelProperty(value = "下一等级需要积分", required = true, example = "100")
        private String pointsToNextTier;
        @JsonIgnore
        @ApiModelProperty(hidden = true)
        private Timestamp createdTime;
        @ApiModelProperty(value = "成为会员多少天", required = true, example = "100")
        private Long registrationDate;

        public Long getRegistrationDate() {
            if (Objects.nonNull(registrationDate)) {
                return registrationDate;
            } else if (Objects.nonNull(createdTime)) {
                createdTime.toInstant();
                final LocalDateTime registerDate = LocalDateTime.ofInstant(createdTime.toInstant(), ZoneId.systemDefault());
                return Duration.between(registerDate, LocalDateTime.now()).toDays();
            }
            return null;
        }
    }

    @RequestMapping(path = "/getloyaltyPointsHis", method = RequestMethod.POST)
    public CommonResponse getloyaltyPointsHis(HttpServletRequest req) {
        String token = req.getAttribute("token").toString();//获取token
        return consumerService.getloyaltyPointsHis(token);//更新用户信息
    }

    @RequestMapping(path = "/test", method = RequestMethod.POST)
    public CommonResponse getInvalidCoupons() {
        Consumer consumer = new Consumer();
        consumer.setFirstName("123");
        consumer.setLastName("456");
        consumer.setEmailAddress("32432@sina.com");
        ConsumerFindResponse rlt = restTemplate.postForObject(URLConstants.ACCTOUNTS_REGISTER_LAYER, consumer,
                ConsumerFindResponse.class);
        return null;
    }

    private Consumer getPhones(Consumer consumer, String id, String token, String encryptedData, String iv) {
        if (consumer == null) {
            consumer = new Consumer();
            consumer.setId(id);
        }
        if (StringUtils.isNotBlank(encryptedData) && StringUtils.isNotBlank(iv)) {
            String key = this.getSessionKey(token);
            log.info("AES----" + key + "--" + iv + "---" + encryptedData);

            String rlt;
            try {
                rlt = AESUtils.decrypt(encryptedData, key, iv);
            } catch (Exception e) {
                throw new BusinessException("session_key已经过期，请重新登录");
            }
            JSONObject data = new JSONObject(rlt);
            if (data.has("purePhoneNumber")) {
                String phone = data.getString("purePhoneNumber");
                String contryCode = CommonConstants.MOBILE_PHONE_AREA_CODE_ADD + data.getString("countryCode");
                consumer.setMobileAreaCode(contryCode);
                consumer.setMobilePhone(phone);
                consumer.setIsOauth(CommonConstants.COMMON_CODE_YES);
                return consumer;
            }
        }
        consumer.setIsOauth(CommonConstants.COMMON_CODE_NO);
        return consumer;
    }

    private String getSessionKey(String token) {
        if (token != null) {
            String value = redisTemplate.opsForValue().get(CommonConstants.SESSION_PRE + token);
            if (value != null) {
                return new JSONObject(value).getString("session_key");
            }
        }
        return null;

    }


}
