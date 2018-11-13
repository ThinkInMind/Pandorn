package com.d1m.social.wechat.pandora.integration.layer.api.util;

public class CommonConstants {

	// gigya 的 apikey
	public static  String gigyaApiKey ;
	public static String userKey;
	public static String secret;
	
	public static String getGigyaApiKey() {
		return System.getProperty("gigya.apiKey");
	}
	public static String getUserKey() {
		return System.getProperty("gigya.userKey");
	}
	public static String getSecret() {
		return System.getProperty("gigya.secret");
	}
	
	public static String getGrant_type() {
		return System.getProperty("grant_type");
	}
	public static String getClient_id() {
		return System.getProperty("client_id");
	}
	public static String getClient_secret() {
		return System.getProperty("client_secret");
	}
	public static String getResource() {
		return System.getProperty("resource");
	}
	
	
	
	public static String HTTP_STATUS_CODE_SUCCESS = "successful";
	public static String HTTP_STATUS_CODE_FAIL = "fail";
}
