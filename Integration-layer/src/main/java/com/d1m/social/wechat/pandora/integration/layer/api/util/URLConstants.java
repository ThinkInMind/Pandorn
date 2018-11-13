package com.d1m.social.wechat.pandora.integration.layer.api.util;

public class URLConstants {

    public static String domain;

    public static String getDomain() {
        return EnvUtils.getProperty("Data_Center.domain");
    }

    public static String scvcrm_domain;

    public static String scvcrm_token;

    public static String getScvcrm_domain() {
        return EnvUtils.getProperty("scvcrm.domain");
    }

    //https://login.microsoftonline.com/656793e6-d51d-4bb2-b5fa-c66ddd181a40/oauth2/token
    public static String getScvcrm_token() {
        return EnvUtils.getProperty("scvcrm.token.url");
    }

    public static String ACCOUNTS_SEARCH = "https://accounts." + URLConstants.getDomain() + "/accounts.search";
    public static String ACCOUNTS_INITREGISTRATION = "https://accounts." + URLConstants.getDomain() + "/accounts.initRegistration";
    public static String ACCOUNTS_REGISTER = "https://accounts." + URLConstants.getDomain() + "/accounts.register";
    public static String ACCOUNTS_LOGIN = "https://accounts." + URLConstants.getDomain() + "/accounts.login";
    public static String CONSUMER_FIND_BY_COUNTRY = URLConstants.getScvcrm_domain() + "/api/v1/consumer/findByCountry";
    public static String CONSUMER_FIND = URLConstants.getScvcrm_domain() + "/api/v1/consumer/find";
    public static String CONSUMER_CREATE = URLConstants.getScvcrm_domain() + "/api/v1/consumer";
    public static String CONSUMER_UPDATE = URLConstants.getScvcrm_domain() + "/api/v1/consumer/";
    public static String SCRM_TOKEN = URLConstants.getScvcrm_token();

}
