package cn.d1m.pandora.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cn.d1m.pandora.service.WeChatService;
@Service("weChatService")
public class WeChatServiceImpl implements WeChatService {

	 private static String JSCODEURL = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type=authorization_code";

	 @Autowired
		private RestTemplate restTemplate;
	@Override
	public JSONObject jscode2session(String appid, String secret, String js_code) {
		 String response = restTemplate.getForObject(JSCODEURL,String.class, appid, secret, js_code);
	     return new JSONObject(response);
//		return new JSONObject("{\"session_key\":\"MwWRAANM\\/5Q4bcYVuFkqgw==\",\"openid\":\"oRGE65FcLtCunvkWCQqHJb1Vgb1o\"}");
	}

}
