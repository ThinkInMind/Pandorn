package cn.d1m.pandora.utils;

public class CommonConstants {

    // gigya 的 apikey


    public static int getVerifyCodeExpireTime() {
        return Integer.parseInt(System.getProperty("verifyCode.expireTime"));
    }

    public static final String getGigyaApiKey() {
        return System.getProperty("gigya.apiKey");
    }

    public static final String getUserKey() {
        return System.getProperty("gigya.userKey");
    }

    public static final String getSecret() {
        return System.getProperty("gigya.secret");
    }

    public static final String getPandoraSMSUID() {
        return System.getProperty("pandora.SMS.UID");
    }

    public static final String getPandoraSMSUserName() {
        return System.getProperty("pandora.SMS.userName");
    }

    public static final String getPandoraSMSPassword() {
        return System.getProperty("pandora.SMS.password");
    }

    public static final String getRedis_data_timeout() {
        return System.getProperty("redis.data.timeout.min");
    }

    public static final String getWeChatAppId() {
        return System.getProperty("weChat.appId");// "wxe4aed41ad2f36ce8";
    }

    public static final String getWeChatSecret() {
        return System.getProperty("weChat.secret");// "ff2709684a9dea46345222dfd9ebbf28";
    }

    public static String getScvcrmSourceId() {
        return System.getProperty("scvcrm.sourceId");
    }

    public static final String COMMON_CODE_YES = "yes";//
    public static final String COMMON_CODE_NO = "no";//

    public static final String COMMON_CODE_CN = "CN";

    public static final String CUNSUMER_STATUS_PENDING = "pending";
    public static final String CUNSUMER_STATUS_REGISTERED = "registered";//for new users + POS consumers
    public static final String CUNSUMER_SOURCEID_WE = "WE";
    public static final String CUNSUMER_SOURCEID_WPOS = "WPOS";
    public static final String CUNSUMER_SOURCEID_WSITE = "WSITE";

    public static final String CUNSUMER_SOURCEID_DEFAULT = "SCrmWeChat";

    public static final String CUNSUMER_STATUS_BOUND = "bound";//bound (for brand-site consumers)
    public static final String HTTP_STATUS_CODE_SUCCESS = "successful";
    public static final String HTTP_STATUS_CODE_FAIL = "fail";
    public static final String HTTP_STATUS_CODE_200 = "200";//成功
    public static final String HTTP_STATUS_CODE_201 = "201";//201
    public static final String HTTP_STATUS_CODE_409 = "409";
    public static final String BRAND_SITE_LOGIN_FLAG = "brand_site_login";
    public static final String REGIST_NEW_FLAG = "regist_new";
    public static final String REGIST_UPDATE_FLAG = "regist_update";
    public static final String REGIST_FLAG = "regist";
    public static final String SESSION_PRE = "pandora:miniapp-session:";
    public static final String OPENID_PRE = "pandora:miniapp-openid:";
    public static final String MOBILE_PHONE_AREA_CODE_ADD = "+";//201
    public static final String MOBILE_PHONE_AREA_CODE_86 = "+86";//201

    public static final String TOKEN_PRE = "token-";

    public static final String X_SESSION_TOKEN = "X-Session-Token";

}
