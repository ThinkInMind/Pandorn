package cn.d1m.pandora.utils;

public class URLConstants {


    public static String getSmsUrl() {
        return EnvUtils.getProperty("pandora.sms.url");
    }


    public static final String ACCTOUNTS_SEARCH_lAYER = "/api/integrationlayer/accounts.search";

    public static final String ACCTOUNTS_LOGIN_lAYER = "/api/integrationlayer/accounts.login";

    //	public static final String  ACCTOUNTS_REGISTER = URLConstants.getIntegration_layer_domain()+ "/api/integrationlayer/accounts.register";
    public static final String ACCTOUNTS_REGISTER_LAYER = "/api/integrationlayer/accounts.register";

    public static final String CONSUMER_FIND_BY_CONTRY_LAYER = "/api/integrationlayer/consumer/findByContry";

    public static final String CONSUMER_FIND_LAYER = "/api/integrationlayer/consumer/find";

    public static final String CONSUMER_ACCTOUNTS_REGISTER_LAYER = "/api/integrationlayer/accounts.register";


    public static final String CONSUMER_CREATE_LAYER = "/api/integrationlayer/consumer/create";

    public static final String CONSUMER_UPDATE_LAYER = "/api/integrationlayer/consumer/update";

}
