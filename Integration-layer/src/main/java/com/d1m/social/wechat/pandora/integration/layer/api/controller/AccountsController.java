package com.d1m.social.wechat.pandora.integration.layer.api.controller;

import com.d1m.social.wechat.pandora.integration.layer.api.entry.Consumer;
import com.d1m.social.wechat.pandora.integration.layer.api.util.CommonUtil;
import com.d1m.social.wechat.pandora.integration.layer.api.util.EnvUtils;
import com.d1m.social.wechat.pandora.integration.layer.api.util.URLConstants;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Random;

@RestController
@RequestMapping(path = "/api/integrationlayer/")
public class AccountsController {
    @Autowired
    public RestTemplate restTemplate;


    Logger log = LoggerFactory.getLogger(AccountsController.class);

    @RequestMapping(path = "/accounts.search", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public String accountsSearch(@RequestParam String mobilePhone, @RequestParam(required = false) String emailAddress) {

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        String query = " SELECT UID,profile,isActive,socialProviders from accounts where profile.phones.number = '" + mobilePhone + "' and profile.phones.type = 'mobile'  ";
        if (StringUtils.isNotBlank(emailAddress)) {
            query += " and email='" + emailAddress + "'";
        }
        param.add("apikey", EnvUtils.getProperty("gigya.apiKey"));

        param.add("userkey", EnvUtils.getProperty("gigya.userKey"));
        param.add("secret", EnvUtils.getProperty("gigya.secret"));
        param.add("query", query);
        log.debug("accounts.search ->mobilePhone :" + mobilePhone + " emailAddress :" + emailAddress);
        String rlt = restTemplate.postForObject(URLConstants.ACCOUNTS_SEARCH, param, String.class);
//		JSONObject json = new JSONObject(rlt);
        log.debug("accounts.search" + rlt);
        return rlt;
    }

    @RequestMapping(path = "/accounts.register", method = RequestMethod.POST)
    public String accountsRegister(@Valid @RequestBody Consumer consumer) {

        MultiValueMap<String, Object> paramInit = new LinkedMultiValueMap<String, Object>();
        paramInit.add("apikey", EnvUtils.getProperty("gigya.apiKey"));
        String initRlt = restTemplate.postForObject(URLConstants.ACCOUNTS_INITREGISTRATION, paramInit, String.class);
        JSONObject json = new JSONObject(initRlt);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        log.info("regToken" + initRlt);
        param.add("apikey", EnvUtils.getProperty("gigya.apiKey"));
        param.add("regToken", json.getString("regToken"));

        param.add("finalizeRegistration", true);
        JSONObject jsonProfile = new JSONObject();
        jsonProfile.put("firstName", consumer.getFirstName());
        jsonProfile.put("lastName", consumer.getLastName());
        jsonProfile.put("email", consumer.getEmailAddress());
        jsonProfile.put("phones", new JSONObject().put("number", consumer.getMobilePhone()));
        jsonProfile.put("gender", CommonUtil.getTransformGender(consumer.getGender()));
        JSONObject jsonData = new JSONObject();
        jsonData.put("terms", "");
        jsonData.put("birthDate", CommonUtil.getBirthday(consumer.getBirthdayYear(), consumer.getBirthdayMonth(), consumer.getBirthdayDay()));
        param.add("data", jsonData.toString());
        param.add("profile", jsonProfile.toString());
        param.add("finalizeRegistration", true);
        param.add("email", consumer.getEmailAddress());
        param.add("password", String.format("%08d", new Random().nextInt(100000000)));
        String rlt = restTemplate.postForObject(URLConstants.ACCOUNTS_REGISTER, param, String.class);
        log.info(URLConstants.ACCOUNTS_REGISTER + ":" + rlt + " param:" + param);
        return rlt;
    }

    @RequestMapping(path = "/accounts.login", method = RequestMethod.POST)
    public String accountsLogin(@Valid Consumer consumer, BindingResult bindingResult) {


        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("apikey", EnvUtils.getProperty("gigya.apiKey"));
        param.add("userkey", EnvUtils.getProperty("gigya.userKey"));
        param.add("loginID", consumer.getLoginID());
        param.add("password", consumer.getPassword());

        String rlt = restTemplate.postForObject(URLConstants.ACCOUNTS_LOGIN, param, String.class);
        log.debug("accounts.login" + rlt);
        return rlt;
    }
}
