package com.d1m.social.wechat.pandora.integration.layer.api.controller;

import com.d1m.social.wechat.pandora.integration.layer.api.entry.Consumer;
import com.d1m.social.wechat.pandora.integration.layer.api.entry.scv.ConsumerFindResponse;
import com.d1m.social.wechat.pandora.integration.layer.api.entry.scv.ErrorInfo;
import com.d1m.social.wechat.pandora.integration.layer.api.util.AccessTokenUtil;
import com.d1m.social.wechat.pandora.integration.layer.api.util.CommonUtil;
import com.d1m.social.wechat.pandora.integration.layer.api.util.URLConstants;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping(path = "/api/integrationlayer/consumer")
public class ConsumerController {
    Logger log = LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public RestTemplate restHttpTemplate;

    @RequestMapping(path = "/create", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> createConsumer(@RequestBody Consumer consumer, HttpResponse rsp) {
        JSONObject content = new JSONObject();//放入body中的json参数
        content.put("sourceId", consumer.getSourceId());
        content.put("firstName", consumer.getFirstName());
        content.put("lastName", consumer.getLastName());
        content.put("emailAddress", consumer.getEmailAddress());
        content.put("country", consumer.getCountry());
        content.put("gender", consumer.getGender());
        content.put("lastModifiedSource", consumer.getSource());
        content.put("mobilePhone", consumer.getMobilePhone());
        content.put("birthdayDay", consumer.getBirthdayDay());
        content.put("birthdayMonth", consumer.getBirthdayMonth());
        content.put("birthdayYear", consumer.getBirthdayYear());
        ResponseEntity<ConsumerFindResponse> rlt = null;
        for (int i = 0; i < 2; i++) {
            HttpHeaders heard = new CommonUtil().getHttpHeaders();
            heard.add("x-return-data", "all");
            HttpEntity<String> requestEntity = new HttpEntity<String>(content.toString(), heard);
            log.info("consumer.create " + content.toString());
            try {
                rlt = restTemplate.postForEntity(URLConstants.CONSUMER_CREATE, requestEntity, ConsumerFindResponse.class);
                log.info("consumer.create " + rlt.getBody().getMobilePhone() + rlt.getBody().getEmailAddress() + rlt.getBody().getId());
                break;
            } catch (HttpClientErrorException e) {
                if (HttpStatus.UNAUTHORIZED.value() == e.getRawStatusCode()) {
                    //token 有问题需要刷新
                    AccessTokenUtil.getInstance().getAccessToken(true);
                    continue;
                }
                log.error("" + content.toString(), e);
                rsp.setStatusCode(e.getRawStatusCode());
                byte[] bytes = e.getResponseBodyAsByteArray();
                log.error(new String(bytes).replace("\u0000", ""), e);
                return new ResponseEntity<String>(new String(bytes).replace("\u0000", ""), HttpStatus.valueOf(e.getRawStatusCode()));
            }
        }
        if (rlt == null) {
            return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ConsumerFindResponse>(rlt.getBody(), HttpStatus.OK);
    }

    public static void main(String[] args) {
        System.out.println(new JSONObject().valueToString(new ConsumerFindResponse()));
    }

    @RequestMapping(path = "/test", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public void test() {
        String rlt = restTemplate.patchForObject("https://test.scvapi.pandora.net/api/v1/consumer/5cc9d271-2d95-4dc7-bc0e-3419d7fc7d02", null, String.class);
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> updateConsumer(@RequestBody Consumer consumer) {
        JSONObject content = new JSONObject();//放入body中的json参数
//        content.put("sourceId", CommonConstants.getScvcrmSourceId());
        content.put("firstName", consumer.getFirstName());
        content.put("lastName", consumer.getLastName());
        content.put("emailAddress", consumer.getEmailAddress());
        content.put("country", consumer.getCountry());
        content.put("gender", consumer.getGender());
        content.put("lastModifiedSource", consumer.getSource());
        content.put("mobilePhone", consumer.getMobilePhone());
        content.put("birthdayDay", consumer.getBirthdayDay());
        content.put("birthdayMonth", consumer.getBirthdayMonth());
        content.put("birthdayYear", consumer.getBirthdayYear());
        ConsumerFindResponse rlt = null;
        for (int i = 0; i < 2; i++) {
            HttpHeaders heard = new CommonUtil().getHttpHeaders();
            heard.add("x-return-data", "all");
            HttpEntity<String> requestEntity = new HttpEntity<String>(content.toString(), heard);
            log.info("consumer.getCrmid()" + consumer.getCrmid());
            try {
                rlt = restTemplate.patchForObject(URLConstants.CONSUMER_UPDATE + consumer.getCrmid(), requestEntity, ConsumerFindResponse.class);

            } catch (HttpClientErrorException e) {
                if (HttpStatus.UNAUTHORIZED.value() == e.getRawStatusCode()) {
                    //token 有问题需要刷新
                    AccessTokenUtil.getInstance().getAccessToken(true);
                    continue;
                }

                byte[] bytes = e.getResponseBodyAsByteArray();
                log.error(new String(bytes).replace("\u0000", "") + "--" + content.toString(), e);
                return new ResponseEntity<String>(new String(bytes).replace("\u0000", ""), HttpStatus.valueOf(e.getRawStatusCode()));
            }
            break;
        }
        return new ResponseEntity<ConsumerFindResponse>(rlt, HttpStatus.OK);

    }

    /**
     * +8613918261266
     * +8613918261200
     *
     * @param consumer
     * @param rsp
     * @return
     */
    @RequestMapping(path = "/findByContry", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> findConsumerByContry(Consumer consumer, HttpResponse rsp) {
        JSONObject content = new JSONObject();//放入body中的json参数
        content.put("mobilePhoneFormatted", consumer.getMobilePhone());
        content.put("country", "CN");

        ResponseEntity<ConsumerFindResponse> rlt = null;
        for (int i = 0; i < 2; i++) {
            try {
                HttpEntity<String> requestEntity = new HttpEntity<String>(content.toString(), new CommonUtil().getHttpHeaders());
                rlt = restTemplate.postForEntity(URLConstants.CONSUMER_FIND_BY_COUNTRY, requestEntity, ConsumerFindResponse.class);
                break;
            } catch (HttpClientErrorException e) {
                if (HttpStatus.UNAUTHORIZED.value() == e.getRawStatusCode()) {
                    //token 有问题需要刷新
                    AccessTokenUtil.getInstance().getAccessToken(true);
                    continue;
                }
                if (HttpStatus.NOT_FOUND.value() != e.getRawStatusCode()
                        || HttpStatus.CONFLICT.value() != e.getRawStatusCode()) {
                    log.error(content.toString(), e);
                }
                ConsumerFindResponse findRsp = new ConsumerFindResponse();
                findRsp.setErr(new ErrorInfo(e.getRawStatusCode() + "", new String(e.getResponseBodyAsByteArray()).replace("\u0000", "")));
                return new ResponseEntity<ConsumerFindResponse>(findRsp, HttpStatus.OK);
            }
        }
        if (rlt == null) {
            return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
        }
        rsp.setStatusCode(rlt.getStatusCodeValue());
        return new ResponseEntity<ConsumerFindResponse>(rlt.getBody(), HttpStatus.OK);
    }

    @RequestMapping(path = "/find", method = RequestMethod.POST)
    public ResponseEntity<?> findConsumer(@RequestParam String emailAddress, HttpResponse rsp) throws UnsupportedEncodingException {
        JSONObject content = new JSONObject();//放入body中的json参数
        content.put("emailAddress", emailAddress);

        ResponseEntity<ConsumerFindResponse> rlt = null;
        for (int i = 0; i < 2; i++) {
            try {
                HttpEntity<String> requestEntity = new HttpEntity<String>(content.toString(), new CommonUtil().getHttpHeaders());
                rlt = restTemplate.postForEntity(URLConstants.CONSUMER_FIND, requestEntity, ConsumerFindResponse.class);
                break;
            } catch (HttpClientErrorException e) {
                if (HttpStatus.UNAUTHORIZED.value() == e.getRawStatusCode()) {
                    //token 有问题需要刷新
                    AccessTokenUtil.getInstance().getAccessToken(true);
                    continue;
                }
                log.error(content.toString(), e);
                ConsumerFindResponse findRsp = new ConsumerFindResponse();
                findRsp.setErr(new ErrorInfo(e.getRawStatusCode() + "", new String(e.getResponseBodyAsByteArray()).replace("\u0000", "")));
                return new ResponseEntity<ConsumerFindResponse>(findRsp, HttpStatus.OK);
            }
        }
        if (rlt == null) {
            return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ConsumerFindResponse>(rlt.getBody(), HttpStatus.OK);
    }
}
