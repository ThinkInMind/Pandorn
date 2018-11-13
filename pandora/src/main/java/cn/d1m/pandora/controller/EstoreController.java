package cn.d1m.pandora.controller;

import cn.d1m.pandora.entry.output.CommonResponse;
import cn.d1m.pandora.service.ConsumerService;
import cn.d1m.pandora.service.LoginService;
import cn.d1m.pandora.service.impl.WeChatServiceImpl;
import cn.d1m.pandora.utils.AESUtils;
import cn.d1m.pandora.utils.CommonConstants;
import org.hibernate.validator.constraints.NotBlank;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * EstoreController
 *
 * @author Jovi gu
 */
@RestController
public class EstoreController {
    static final long EXPIRE_TIME = 1; //1天

    private Logger log = LoggerFactory.getLogger(EstoreController.class);
    @Autowired
    public ConsumerService consumerService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    WeChatServiceImpl weChatService;
    @Autowired
    LoginService loginService;

    @Autowired
    private Environment environment;

    /**
     * 用户登陆
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "onLogin/{code}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse login(@PathVariable(name = "code") String code) {
        //获取 sessionKey
        JSONObject res = weChatService.jscode2session(environment.getProperty("weChat.appId"), environment.getProperty("weChat.secret"), code);
        if (log.isDebugEnabled()) {
            log.debug(res.toString());
        }
        //返回token
        JSONObject token = loginService.getSession(res);
        if (token != null) {
            CommonResponse rsp = consumerService.verify(res.getString("id"));
            if (rsp.getData() != null) {
                log.debug("*****" + rsp.getData().toString());
                Map<String, Object> map;
                if (rsp.getData() instanceof Map) {
                    map = (Map) rsp.getData();
                } else {
                    JSONObject data = new JSONObject(rsp.getData().toString());
                    map = data.toMap();
                }
                map.putAll(token.toMap());
                rsp.setData(map);
            }
            return rsp;
        } else {
            return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "login fail");
        }
    }

    @RequestMapping(path = "/getPhones", method = RequestMethod.POST)
    public CommonResponse getPhones(@Valid @NotBlank(message = "encrypted is null ") @RequestParam String encryptedData
            , @NotBlank(message = "iv is null ") @RequestParam String iv, HttpServletRequest req) {
        String token = req.getAttribute("token").toString();//获取token
        String key = this.getSessionKey(token);
        try {
            String rlt = AESUtils.decrypt(encryptedData, key, iv);
            JSONObject data = new JSONObject(rlt);
            String phone = data.getString("purePhoneNumber");
            String contryCode = CommonConstants.MOBILE_PHONE_AREA_CODE_ADD + data.getString("countryCode");
            return null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //	@RequestMapping(path="/error", method=RequestMethod.POST )
//	public CommonResponse error() {
//		return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL,"system error!");
//	}
    private String getSessionKey(String token) {
        if (token != null) {
            String value = redisTemplate.opsForValue().get(CommonConstants.SESSION_PRE + token);
            if (value != null) {
                return new JSONObject(value).getString("session_key");
            }
        }
        return null;
    }
}
