package com.d1m.social.wechat.pandora.integration.layer.api.util;

import com.d1m.social.wechat.pandora.integration.layer.api.entry.scv.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class AccessTokenUtil {
    Logger log = LoggerFactory.getLogger(AccessTokenUtil.class);
    private String redisTokenName = "SCVCRM_TOKEN";
    private volatile static AccessTokenUtil instance;

    private static RestTemplate restTemplate;
    private static StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RestTemplate restTemplateTmp;
    @Autowired
    private StringRedisTemplate stringRedisTemplateTmp;

    private AccessTokenUtil() {
        log.debug("AccessTokenUtil has loaded");
    }

    @PostConstruct
    public void beforeInit() {
        restTemplate = restTemplateTmp;
        stringRedisTemplate = stringRedisTemplateTmp;
    }

    public static AccessTokenUtil getInstance() {
        if (instance == null) {
            synchronized (AccessTokenUtil.class) {
                if (instance == null) {
                    instance = new AccessTokenUtil();
                }
            }
        }
        return instance;
    }

    public String getAccessToken(boolean... bs) {
        String token = stringRedisTemplate.opsForValue().get(redisTokenName);
        if (token == null || (bs != null && bs.length > 0 && bs[0])) {
            synchronized (AccessTokenUtil.class) {
                token = stringRedisTemplate.opsForValue().get(redisTokenName);
                if (bs != null && bs.length > 0 && bs[0] && stringRedisTemplate.getExpire(redisTokenName, TimeUnit.SECONDS) < 3500) {
                    //强制刷新
                    log.info("强制刷新token!" + stringRedisTemplate.getExpire(redisTokenName, TimeUnit.SECONDS));
                    token = null;
                }
                if (token == null) {
                    try {
                        AccessTokenResponse rsp = refushToken();
                        if (rsp != null && rsp.getAccess_token() != null) {
                            token = rsp.getAccess_token();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return token;
    }

    public AccessTokenResponse refushToken() {
        log.info("refush token!");
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("grant_type", EnvUtils.getProperty("grant_type"));
        param.add("client_id", EnvUtils.getProperty("client_id"));
        param.add("client_secret", EnvUtils.getProperty("client_secret"));
        param.add("resource", EnvUtils.getProperty("resource"));
//		param.add("grant_type", "client_credentials");
//		param.add("client_id", "f6829e3c-383c-4096-b434-edb61a2a1f64");
//		param.add("client_secret", "utpOI6klvH408iZ+ivqOWsSd3ciSCMCeLVTocOkuPY0=");
//		param.add("resource", "https://PandoraNet.onmicrosoft.com/ed2b81d2-37cb-4091-a6e4-52566c7be824");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(param, requestHeaders);
        AccessTokenResponse rlt = null;
        for (int i = 0; i < 2; i++) {
            rlt = restTemplate.postForObject(URLConstants.SCRM_TOKEN, requestEntity, AccessTokenResponse.class);
            if (rlt != null && rlt.getAccess_token() != null) {
                stringRedisTemplate.opsForValue().set(redisTokenName, rlt.getAccess_token(), rlt.getExpires_in() - 20, TimeUnit.SECONDS);
                break;
            } else {
                log.info("refush token! 2 " + rlt);
            }
        }
        return rlt;
    }

}
