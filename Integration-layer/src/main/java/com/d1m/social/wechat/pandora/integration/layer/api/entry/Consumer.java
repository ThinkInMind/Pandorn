package com.d1m.social.wechat.pandora.integration.layer.api.entry;


import lombok.Data;
import org.json.JSONObject;

import java.io.Serializable;

@Data
public class Consumer implements Serializable {


    /**
     *
     */
    private String loginID;
    private String password;

    private String isOauth;


    private String emailAddress;//邮件
    private String mobilePhone;//电话
    private String id;//32UUID
    private String firstName;//名
    private String lastName;//姓

    private String mobileAreaCode;//区号
    private String birthdayDay;//生日
    private String birthdayMonth;//月
    private String birthdayYear;
    private String birthday;
    private String titleSalutation;//称谓
    private String gender;//性别
    private String vipNumber;// VIP编号

    private String loyaltyPoints;//
    private String loyaltyTier;// VARC
    private String loyaltyPointsExpiryDate;//
    private String nextLoyaltyTier;//
    private String pointsToNextTier;//
    private String previousLoyaltyTier;//
    private String lastBirthdayDiscountRedemptionDate;//
    private String openid;//
    private String unionid;//
    private String status;//
    private String crmid;//
    private String channel;//
    private String source;//
    private String createdTime;//
    private String lastUpdatedTime;//
    private String country;
    private String verificationCode;// 验证码
    private String sourceId;

    public static JSONObject response(Consumer consumer) {
        JSONObject json = new JSONObject();
        json.put("firstName", consumer.getFirstName());
        json.put("lastName", consumer.getLastName());
        json.put("titleSalutation", consumer.getTitleSalutation());
        json.put("birthdayDay", consumer.getBirthdayDay());
        json.put("birthdayMonth", consumer.getBirthdayMonth());
        json.put("birthdayYear", consumer.getBirthdayYear());
        json.put("gender", consumer.getGender());
        json.put("emailAddress", consumer.getEmailAddress());
        json.put("mobilePhone", consumer.getMobilePhone());
        json.put("country", consumer.getCountry());
        json.put("createdTime", consumer.getCreatedTime());
        json.put("status", consumer.getStatus());
        json.put("lastUpdatedTime", consumer.getLastUpdatedTime());
        json.put("vipNumber", consumer.getVipNumber());
        json.put("loyaltyTier", consumer.getLoyaltyTier());
        json.put("loyaltyPoints", consumer.getLoyaltyPoints());
        json.put("pointsToNextTier", consumer.getPointsToNextTier());
        json.put("pointsToNextTier", consumer.getSource());
        json.put("crmid", consumer.getCrmid());
        return json;
    }

    public static JSONObject responseLoyaltyPoints(Consumer consumer) {
        JSONObject json = new JSONObject();
        json.put("vipNumber", consumer.getVipNumber());
        json.put("loyaltyTier", consumer.getLoyaltyTier());
        json.put("loyaltyPoints", consumer.getLoyaltyPoints());
        json.put("pointsToNextTier", consumer.getPointsToNextTier());
        json.put("registrationDate", consumer.getCreatedTime());
//		json.put("r", consumer.getSource());
        return json;
    }

    public static JSONObject responseCommon(Consumer consumer) {
        JSONObject json = new JSONObject();
        json.put("token", consumer.getId());

        return json;
    }

    public static JSONObject queryConsumer(Consumer consumer) {
        JSONObject json = new JSONObject();
        json.put("token", consumer.getId());
        json.put("mobilePhone", consumer.getMobilePhone());
        return json;
    }
}
