package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.dao.ConsumerMapper;
import cn.d1m.pandora.entry.Consumer;
import cn.d1m.pandora.entry.output.AccountsSearchResponse;
import cn.d1m.pandora.entry.output.CommonResponse;
import cn.d1m.pandora.entry.output.scv.ConsumerFindResponse;
import cn.d1m.pandora.feign.IntegrationLayerService;
import cn.d1m.pandora.service.ConsumerService;
import cn.d1m.pandora.service.VerifyCodeService;
import cn.d1m.pandora.utils.CommonConstants;
import cn.d1m.pandora.utils.ConsumerErrorCode;
import cn.d1m.pandora.utils.URLConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

@Service("consumerService")
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private IntegrationLayerService integrationLayerService;

    @Autowired
    private Environment environment;

//    @Autowired //注入一个DiscoveryClient
//    private DiscoveryClient discoveryClient;
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private String serviceUrl() {
//        List<ServiceInstance> list = discoveryClient.getInstances("integration-layer");
//        return list.stream().filter(i ->
//                !CollectionUtils.isEmpty(i.getMetadata()) &&
//                        i.getMetadata()
//                                .values()
//                                .stream()
//                                .allMatch(k -> k.startsWith(environment.getProperty("spring.profiles.active")))
//        ).findFirst()
//                .map(u -> u.getUri().toString())
//                .orElseThrow(() -> new RuntimeException("Integration-layer discovery error"));
//    }

    @Override
    public CommonResponse queryConsumer(Consumer queryConsumer) {
        Consumer existConsumer = consumerMapper.findById(queryConsumer.getId());
        if (CommonConstants.COMMON_CODE_NO.equals(queryConsumer.getIsOauth())) {
            Consumer rltConsumer = saveConsumerIsOaurh(queryConsumer, existConsumer);
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS,
                    Consumer.responseCommon(rltConsumer).toMap(), null);
        } else {
            if (queryConsumer.getMobilePhone() == null) {
                return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "mobile phone is null");
            }
        }
        boolean firstSearchrRlt = false;
        // 查询获取是否有账户信息
        AccountsSearchResponse rlt = null;
        try {
            rlt = getAccountsSearchRsp(queryConsumer.getMobilePhone());
        } catch (HttpClientErrorException e) {
            log.warn("url:{},parma:{}{}", URLConstants.ACCTOUNTS_SEARCH_lAYER, queryConsumer.getMobilePhone(), new String(e.getResponseBodyAsByteArray()), e);
            if (existConsumer == null) {
                firstSearchrRlt = true;
            }
        }

        if (rlt != null && rlt.getTotalCount() > 0) {
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "mobile/email exists");
        } else {
            Consumer rltConsumer = saveConsumer(queryConsumer, existConsumer);
            if (firstSearchrRlt) {
                return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "accounts search Connection refused!");
            }
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS,
                    Consumer.response(rltConsumer).toString(), null);
        }
    }

    @Override
    public Consumer findByOpenid(String open_id) {

        return consumerMapper.findByOpenid(open_id);
    }

    @Override
    public CommonResponse beforeLoginBound(Consumer queryConsumer) {
        Consumer existConsumer = consumerMapper.findById(queryConsumer.getId());
        Consumer rltConsumer = saveConsumerIsOaurh(queryConsumer, existConsumer);
        return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS,
                Consumer.responseCommon(rltConsumer).toMap(), null);
    }

    @Override
    public CommonResponse registAdd(Consumer consumer) {
        Consumer tmpConsumer = consumerMapper.findById(consumer.getId());
        if (tmpConsumer == null) {
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "no consumer ");
        }
        consumer.setIsOauth(tmpConsumer.getIsOauth());
        if (!checkConsumerMobile(consumer)) {
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL,
                    "mobile is null  or sms code invalid/expired!  ");
        }
        consumer.setCountry(CommonConstants.COMMON_CODE_CN);
        consumerMapper.update(consumer);

        // 异步获取用户信息
        return registDealCRMConsumerWithMobile(consumer);

    }

    private CommonResponse registDealCRMConsumerWithMobile(Consumer consumer) {
//        try {
        ConsumerFindResponse rlt = findByCountryAmobile(consumer.getMobilePhone());


        if (rlt.getErr() != null) {
            if ((HttpStatus.NOT_FOUND.value() + "").equals(rlt.getErr().getCode())) {
                //无人注册该手机号
                log.info("无人注册该手机号" + consumer.getMobilePhone());
                return addSCVConsumer(consumer, CommonConstants.REGIST_NEW_FLAG);
            } else return getCommonResponse(consumer, rlt);
        } else {
            // 含一个人的信息
            log.info("含一个人的信息" + consumer.getMobilePhone());
            return registedUpdateSCVConsumer(consumer, rlt, CommonConstants.REGIST_UPDATE_FLAG);
        }
//
//        } catch (HttpClientErrorException e) {
//            // 连接异常
//            log.warn("url" + URLConstants.CONSUMER_FIND_BY_CONTRY_LAYER + "parma: " + consumer.getMobilePhone()
//                    + " is not  not connect", e);
//            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "connection refused ");
//
//        }

    }

    private CommonResponse loginDealCRMConsumerWithMobile(Consumer consumer) {

//        try {
        ConsumerFindResponse rlt = findByCountryAmobile(consumer.getMobilePhone());
        if (rlt.getErr() != null) {
            if ((HttpStatus.NOT_FOUND.value() + "").equals(rlt.getErr().getCode())) {
                //无人注册该手机号
                //登陆
//                return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "consumer not find   ");
                return CommonResponse.builder()
                        .status(CommonConstants.HTTP_STATUS_CODE_FAIL)
                        .errorCode(ConsumerErrorCode.CONSUMER_NOT_FOUND)
                        .errorInfo
                                ("consumer not find   ")
                        .build();
            } else {
                return getCommonResponse(consumer, rlt);
            }
        } else {
            // 含一个人的信息
            //登陆
            return loginUpdateSCVConsumer(consumer, rlt);
        }
//
//        } catch (HttpClientErrorException e) {
//            // 连接异常
//            log.warn("url" + URLConstants.CONSUMER_FIND_BY_CONTRY_LAYER + "parma: " + consumer.getMobilePhone()
//                    + " is not  not connect", e);
//            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "connection refused ");
//
//        }

    }

    private CommonResponse getCommonResponse(Consumer consumer, ConsumerFindResponse rlt) {
        if ((HttpStatus.CONFLICT.value() + "").equals(rlt.getErr().getCode())) {
            // 多个注册人手机信息
            log.warn("url" + URLConstants.CONSUMER_FIND_BY_CONTRY_LAYER + "parma: " + consumer.getMobilePhone()
                    + " HttpStatus.CONFLICT" + rlt.getErr().getMessage());
//            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "Multiple mobile phones exist  ");
            return CommonResponse.builder()
                    .status(CommonConstants.HTTP_STATUS_CODE_FAIL)
                    .errorCode(ConsumerErrorCode.MOBILE_EXIST)
                    .errorInfo
                            ("Multiple mobile phones exist  ")
                    .build();
        } else {
            // 连接异常
            log.warn("url" + URLConstants.CONSUMER_FIND_BY_CONTRY_LAYER + "parma: " + consumer.getMobilePhone()
                    + " is not  not connect" + rlt.getErr().getCode());
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "connection refused ");
        }
    }

    private CommonResponse loginDealCRMConsumerWithEmail(Consumer consumer) {

//        try {
        ConsumerFindResponse rlt = findByCountryAEmail(consumer.getEmailAddress());
        if (rlt.getErr() != null) {
            if ((HttpStatus.NOT_FOUND.value() + "").equals(rlt.getErr().getCode())) {
                //无人注册该手机号
                //登陆
                return CommonResponse.builder()
                        .status(CommonConstants.HTTP_STATUS_CODE_FAIL)
                        .errorCode(ConsumerErrorCode.CONSUMER_NOT_FOUND)
                        .errorInfo
                                ("consumer not find   ")
                        .build();

            } else if ((HttpStatus.CONFLICT.value() + "").equals(rlt.getErr().getCode())) {
                // 多个注册人手机信息
                log.warn("url" + URLConstants.CONSUMER_FIND_BY_CONTRY_LAYER + "parma: " + consumer.getMobilePhone()
                        + " HttpStatus.CONFLICT" + rlt.getErr().getMessage());
                return CommonResponse.builder()
                        .status(CommonConstants.HTTP_STATUS_CODE_FAIL)
                        .errorCode(ConsumerErrorCode.MOBILE_EXIST)
                        .errorInfo
                                ("Multiple mobile phones exist  ")
                        .build();
            } else {
                // 连接异常
                log.warn("url:" + URLConstants.CONSUMER_FIND_LAYER + " parma: " + consumer.getMobilePhone()
                        + " is not  not connect" + rlt.getErr().getCode());
                return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "connection refused ");
            }
        } else {
            // 含一个人的信息
            //登陆
            return loginUpdateSCVConsumer(consumer, rlt);
        }

//        } catch (HttpClientErrorException e) {
//            // 连接异常
//            log.warn("url" + URLConstants.CONSUMER_FIND_BY_CONTRY_LAYER + "parma: " + consumer.getMobilePhone()
//                    + " is not  not connect", e);
//            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "connection refused ");
//
//        }

    }

    /**
     * 根据手机号码获取用户信息
     *
     * @param mobilePhone
     * @return
     */
    private ConsumerFindResponse findByCountryAmobile(String mobilePhone) {
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("mobilePhone", mobilePhone.startsWith("+") ? mobilePhone
                : ("+86" + mobilePhone));
        param.add("country", CommonConstants.COMMON_CODE_CN);
        return integrationLayerService.consumerFindByContryLayer(param);
//        return restTemplate.postForEntity(serviceUrl() + URLConstants.CONSUMER_FIND_BY_CONTRY_LAYER,
//                param, ConsumerFindResponse.class);
    }

    public ConsumerFindResponse findByCountryAEmail(String emailAddress) {
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("emailAddress", emailAddress);
        return integrationLayerService.consumerFindLayer(param);
//        return restTemplate.postForEntity(serviceUrl() + URLConstants.CONSUMER_FIND_LAYER,
//                param, ConsumerFindResponse.class);
    }

    private CommonResponse registedUpdateSCVConsumer(Consumer consumer, ConsumerFindResponse rsp, String flag) {
        try {
            consumer.setCrmid(rsp.getId());
            Consumer upConsumer = new Consumer();
            //registUpd
            consumer.setSource(CommonConstants.CUNSUMER_SOURCEID_DEFAULT);
            upConsumer.setStatus(CommonConstants.CUNSUMER_STATUS_REGISTERED);
            upConsumer.setSource(CommonConstants.CUNSUMER_SOURCEID_WPOS);

            // scrm注册
//            ConsumerFindResponse rlt = restTemplate.postForObject(serviceUrl() + URLConstants.CONSUMER_UPDATE_LAYER, consumer,
//                    ConsumerFindResponse.class);
            integrationLayerService.consumerUpdateLayer(consumer);
            // 更新一个人的信息

            upConsumer.setId(consumer.getId());
            upConsumer.setCrmid(consumer.getCrmid());

            updateConsumerRegistered(upConsumer);
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS);
        } catch (HttpClientErrorException e) {
            // 连接异常
            log.warn("url:" + URLConstants.CONSUMER_FIND_BY_CONTRY_LAYER + "parma: " + consumer.getMobilePhone()
                    + " is not  not connect", e);
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "connection refused ");

        }
    }

    public CommonResponse loginUpdateSCVConsumer(Consumer consumer, ConsumerFindResponse rsp) {
        try {
            consumer.setCrmid(rsp.getId());
            Consumer upConsumer = new Consumer();
            consumer.setSource(CommonConstants.CUNSUMER_SOURCEID_WSITE);
            upConsumer.setStatus(CommonConstants.CUNSUMER_STATUS_PENDING);
            upConsumer.setSource(CommonConstants.CUNSUMER_SOURCEID_WSITE);
            upConsumer.setFirstName(rsp.getFirstName());
            upConsumer.setLastName(rsp.getLastName());
            upConsumer.setBirthdayDay(rsp.getBirthdayDay());
            upConsumer.setBirthdayMonth(rsp.getBirthdayMonth());
            upConsumer.setBirthdayYear(rsp.getBirthdayYear());
            upConsumer.setEmailAddress(rsp.getEmailAddress());
            if (rsp.getMobilePhone().startsWith(CommonConstants.MOBILE_PHONE_AREA_CODE_ADD)) {
                upConsumer.setMobileAreaCode(rsp.getMobilePhone().substring(0, 3));
                upConsumer.setMobilePhone(rsp.getMobilePhone().substring(3));
            } else {
                upConsumer.setMobilePhone(rsp.getMobilePhone());
            }
            upConsumer.setGender(rsp.getGender());
            // 更新一个人的信息
            upConsumer.setId(consumer.getId());
            upConsumer.setCrmid(rsp.getId());

            updateConsumerLogin(upConsumer);
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS, Consumer.response(upConsumer).toMap(), null);
        } catch (HttpClientErrorException e) {
            // 连接异常
            log.warn("url" + URLConstants.CONSUMER_FIND_BY_CONTRY_LAYER + "parma: " + consumer.getMobilePhone()
                    + " is not  not connect", e);
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "connection refused ");

        }
    }

    /**
     * scv/crm 创建用户
     *
     * @param consumer
     * @return 201 status 创建成功
     */
    public CommonResponse addSCVConsumer(Consumer consumer, String flag) {
        try {
            consumer.setSource(CommonConstants.CUNSUMER_SOURCEID_DEFAULT);//'EStore'   SCRM
            consumer.setSourceId(environment.getProperty("scvcrm.sourceId"));
            // scrm注册
//            ConsumerFindResponse rlt = restTemplate.postForObject(serviceUrl() + URLConstants.CONSUMER_CREATE_LAYER, consumer,
//                    ConsumerFindResponse.class);
            if (log.isDebugEnabled()){
                log.debug(consumer.toString());
            }
            final ConsumerFindResponse rlt = integrationLayerService.consumerCreateLayer(consumer);
            // 更新一个人的信息
            Consumer upConsumer = new Consumer();
            upConsumer.setId(consumer.getId());
            upConsumer.setCrmid(rlt.getId());
            upConsumer.setStatus(CommonConstants.CUNSUMER_STATUS_REGISTERED);
            upConsumer.setSource(CommonConstants.CUNSUMER_SOURCEID_WE);
            updateConsumerRegistered(upConsumer);
            registerAccounts(consumer);
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS);
        } catch (HttpClientErrorException e) {
            // 连接异常
            log.warn("url" + URLConstants.CONSUMER_CREATE_LAYER + "parma: " + consumer.getMobilePhone()
                    + " is not  not connect", e);
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "connection refused ");

        }
    }

    private CommonResponse registerAccounts(Consumer consumer) {
        try {

            // scrm注册
//            ConsumerFindResponse rlt = restTemplate.postForObject(serviceUrl() + URLConstants.ACCTOUNTS_REGISTER_LAYER, consumer,
//                    ConsumerFindResponse.class);
            integrationLayerService.accountsRegisterLayer(consumer);
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS);
        } catch (HttpClientErrorException e) {
            // 连接异常
            log.warn("url" + URLConstants.ACCTOUNTS_REGISTER_LAYER + "parma: " + consumer.getMobilePhone()
                    + " is not  not connect", e);
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "connection refused ");
        }

    }

    private boolean checkConsumerMobile(Consumer consumer) {
        log.info("Captcha is error, mobile ： {}, verification code : {}", consumer.getMobilePhone(), consumer.getVerificationCode());
        if (CommonConstants.COMMON_CODE_YES.equals(consumer.getIsOauth())
                && StringUtils.isNotBlank(consumer.getMobilePhone())) {
            // 微信授权获取手机号
            return true;
        } else if (CommonConstants.COMMON_CODE_NO.equals(consumer.getIsOauth())
                && StringUtils.isNotBlank(consumer.getVerificationCode())) {
            // 手机验证码验证
            if (checkMobileCode(consumer)) {
                return true;
            } else {
                log.warn("Verify code faild");
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean checkMobileCode(Consumer consumer) {

        return verifyCodeService.checkVerifyCode(consumer.getMobilePhone(), consumer.getVerificationCode());
    }

    @Override
    public CommonResponse updateConsumer(Consumer consumer) {
        Consumer existConsumer = consumerMapper.findById(consumer.getId());
        consumer.setCrmid(existConsumer.getCrmid());
//        ConsumerFindResponse rlt = restTemplate.postForObject(serviceUrl() + URLConstants.CONSUMER_UPDATE_LAYER, consumer,
//                ConsumerFindResponse.class);
        integrationLayerService.consumerUpdateLayer(consumer);

        int count = consumerMapper.update(consumer);
        if (count == 0) {
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, Consumer.responseCommon(consumer).toMap(), "no consumer");//返回结果
        }
        return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS, Consumer.responseCommon(consumer).toMap(), null);//返回结果
    }

    @Override
    public int updateConsumerRegistered(Consumer consumer) {

        return consumerMapper.updateConsumerRegistered(consumer);
    }

    @Override
    public int updateConsumerLogin(Consumer consumer) {

        return consumerMapper.update(consumer);
    }

    @Override
    public CommonResponse findByOpenid(String open_id, String mobile_phone) {
        // TODO Auto-generated method stub
        return null;
    }

    // 保存是否授权
    private Consumer saveConsumerIsOaurh(Consumer queryConsumer, Consumer existConsumer) {
        Consumer consumer = new Consumer();
        consumer.setIsOauth(queryConsumer.getIsOauth());
        consumer.setOpenid(queryConsumer.getOpenid());
        consumer.setUnionid(queryConsumer.getUnionid());
        if (CommonConstants.COMMON_CODE_YES.equals(queryConsumer.getIsOauth())) {
            consumer.setMobilePhone(queryConsumer.getMobilePhone());
        }
        if (existConsumer != null) {
            consumer.setId(existConsumer.getId());
            consumerMapper.updateWithOaurh(consumer);
        } else {
            consumerMapper.add(consumer);
        }
        return consumer;
    }

    private Consumer saveConsumer(Consumer queryConsumer, Consumer existConsumer) {
        Consumer consumer = new Consumer();
        consumer.setMobilePhone(queryConsumer.getMobilePhone());
        consumer.setMobileAreaCode(CommonConstants.MOBILE_PHONE_AREA_CODE_86);
        ;
        consumer.setStatus(CommonConstants.CUNSUMER_STATUS_PENDING);
        consumer.setIsOauth(queryConsumer.getIsOauth());
        consumer.setOpenid(queryConsumer.getOpenid());
        consumer.setUnionid(queryConsumer.getUnionid());
        if (existConsumer != null) {
            consumer.setId(existConsumer.getId());
            consumerMapper.update(consumer);
        } else {
            consumerMapper.add(consumer);
        }
        return consumer;
    }

    private AccountsSearchResponse getAccountsSearchRsp(String mobile_phone) {
//        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
//        param.add("mobilePhone", mobile_phone.startsWith("+") ? mobile_phone : (CommonConstants.MOBILE_PHONE_AREA_CODE_86 + mobile_phone));
//        param.add("emailAddress", null);
//        Consumer c = new Consumer();
//        c.setMobilePhone(mobile_phone.startsWith("+") ? mobile_phone : (CommonConstants.MOBILE_PHONE_AREA_CODE_86 + mobile_phone));
//        AccountsSearchResponse rlt = restTemplate.postForObject(serviceUrl() + URLConstants.ACCTOUNTS_SEARCH_lAYER, param,
//                AccountsSearchResponse.class);

        return integrationLayerService.accountsSearchLayer(mobile_phone.startsWith("+") ? mobile_phone : (CommonConstants.MOBILE_PHONE_AREA_CODE_86 + mobile_phone), null);
    }

    @Override
    public CommonResponse add(Consumer consumer) {
        // TODO Auto-generated method stub
        consumerMapper.add(consumer);
        return null;
    }

    @Override
    public CommonResponse login(Consumer consumer, String loginId, String password, String token) {
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        if (!loginId.contains("@") && loginId.length() == 11) {
            loginId = CommonConstants.MOBILE_PHONE_AREA_CODE_86 + loginId;
        }
        param.add("loginID", loginId);
        param.add("password", password);
//        AccountsLoginResponse rlt = restTemplate.postForObject(serviceUrl() + URLConstants.ACCTOUNTS_LOGIN_lAYER, param,
//                AccountsLoginResponse.class);
        final AccountsSearchResponse rlt = integrationLayerService.accountsLoginLayer(param);
        if (rlt != null && CommonConstants.HTTP_STATUS_CODE_200.equals(rlt.getStatusCode())) {
            consumer.setId(token);
            if (loginId.contains("@")) {
                consumer.setEmailAddress(loginId);
                return loginDealCRMConsumerWithEmail(consumer);
            } else {
                consumer.setMobilePhone(loginId);

                return loginDealCRMConsumerWithMobile(consumer);
            }

        } else {
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "Login Error");
        }

    }

    @Override
    public CommonResponse loginUpd(Consumer consumer, String token) {
        Consumer tmpConsumer = consumerMapper.findById(token);
        consumer.setIsOauth(tmpConsumer.getIsOauth());
        if (!checkConsumerMobile(consumer)) {
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL,
                    "mobile is null  or sms code invalid/expired!  ");
        }
        // scrm更新
        consumer.setCrmid(tmpConsumer.getCrmid());
//        ConsumerFindResponse rlt = restTemplate.postForObject(serviceUrl() + URLConstants.CONSUMER_UPDATE_LAYER, consumer,
//                ConsumerFindResponse.class);
        final ConsumerFindResponse rlt = integrationLayerService.consumerUpdateLayer(consumer);
        // 更新一个人的信息
        return loginBaoudFinish(tmpConsumer, rlt);
    }

    public CommonResponse loginBaoudFinish(Consumer consumer, ConsumerFindResponse rsp) {
        try {
            consumer.setCrmid(rsp.getId());
            Consumer upConsumer = new Consumer();
            consumer.setSource(CommonConstants.CUNSUMER_SOURCEID_WSITE);
            upConsumer.setStatus(CommonConstants.CUNSUMER_STATUS_BOUND);
            upConsumer.setSource(CommonConstants.CUNSUMER_SOURCEID_WSITE);
            upConsumer.setFirstName(rsp.getFirstName());
            upConsumer.setLastName(rsp.getLastName());
            upConsumer.setBirthdayDay(rsp.getBirthdayDay());
            upConsumer.setBirthdayMonth(rsp.getBirthdayMonth());
            upConsumer.setBirthdayYear(rsp.getBirthdayYear());
            upConsumer.setEmailAddress(rsp.getEmailAddress());
            if (rsp.getMobilePhone().startsWith(CommonConstants.MOBILE_PHONE_AREA_CODE_ADD)) {
                upConsumer.setMobileAreaCode(rsp.getMobilePhone().substring(0, 3));
                upConsumer.setMobilePhone(rsp.getMobilePhone().substring(3));
            } else {
                upConsumer.setMobilePhone(rsp.getMobilePhone());
            }
            upConsumer.setGender(rsp.getGender());
            // 更新一个人的信息
            upConsumer.setId(consumer.getId());
            upConsumer.setCrmid(rsp.getId());

            updateConsumerLogin(upConsumer);
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS, Consumer.response(upConsumer).toMap());
        } catch (HttpClientErrorException e) {
            // 连接异常
            log.warn("url" + URLConstants.CONSUMER_FIND_BY_CONTRY_LAYER + "parma: " + consumer.getMobilePhone()
                    + " is not  not connect", e);
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "connection refused ");

        }
    }

    @Override
    public CommonResponse getConsumer(String token) {
        Consumer consumer = consumerMapper.findById(token);
        return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS, Consumer.response(consumer).toMap());
    }

    @Override
    public Consumer getloyaltyPoints(String token) {
        return consumerMapper.findById(token);
    }

    @Override
    public CommonResponse getloyaltyPointsHis(String token) {
        Consumer consumer = consumerMapper.findById(token);
        String crmID = consumer.getCrmid();
        // scrm注册
//        ConsumerFindResponse rlt = restTemplate.postForObject(serviceUrl() + URLConstants.CONSUMER_CREATE_LAYER, consumer,
//                ConsumerFindResponse.class);
        integrationLayerService.consumerCreateLayer(consumer);
        return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS, Consumer.responseLoyaltyPoints(consumer).toMap());
    }

    @Override
    public CommonResponse verify(String token) {

        Consumer consumer = consumerMapper.findById(token);// 查询用户信息
        if (consumer == null) {// 判断是否有用户信息，如果没有则需要走注册或绑定流程
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "{\"page\":0}", "no consumer");
        } else if (!(StringUtils.isNotBlank(consumer.getCrmid())
                && (CommonConstants.CUNSUMER_STATUS_REGISTERED.equals(consumer.getStatus())
                || CommonConstants.CUNSUMER_STATUS_BOUND.equals(consumer.getStatus()))
        )) { //判断是否是正在绑定的用户，状态是否为registered 或者 bound ，，如果不是需要走绑定或注册流程
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "{\"page\":1}", "consumer not bound/registered");// 返回其他页面
        }


        return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS, Consumer.response(consumer).toMap(), null);
    }
}
