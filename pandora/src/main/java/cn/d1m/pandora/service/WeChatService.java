package cn.d1m.pandora.service;

import org.json.JSONObject;

public interface WeChatService {
	
//	public String getWeChatPhoneNumber();
	/**
	 * 
	 * @param appid 小程序唯一标识  必填
	 * @param secret 小程序的 app secret  必填
	 * @param js_code 登录时获取的 code 必填
	 * @param grant_type 填写为 authorization_code 必填
	 * @return
	 */
	public JSONObject jscode2session(String appid,String secret,String js_code);
}
