package com.d1m.social.wechat.pandora.integration.layer.api.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class CommonUtil {
	public  HttpHeaders getHttpHeaders() {
		HttpHeaders requestHeaders = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		requestHeaders.setContentType(type);
        
        requestHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer "+AccessTokenUtil.getInstance().getAccessToken());
        return requestHeaders;
	}
	public static String getBirthday( String year ,String month,String day) {
		if(year == null) {
			year = "0000";
		}
		if(month == null) {
			month = "00";
		}else if(month.length() == 1) {
			month = "0"+ month;
		}
		if(day == null) {
			day = "00";
		}else if(day.length() == 1) {
			day = "0" + day;
		}
		return year + month + day;
	}
	public static String getTransformGender(String gender){
		if("Male".equals(gender)) {
			return "m";
		}else if("Female".equals(gender)) {
			return "f";
		}else if("Unspecified".equals(gender)){
			return "u";
		}else {
			return "u";
		}
		
	}
}
