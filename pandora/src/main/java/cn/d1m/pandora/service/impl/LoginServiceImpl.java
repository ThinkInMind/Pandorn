package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.dao.ConsumerMapper;
import cn.d1m.pandora.entry.Consumer;
import cn.d1m.pandora.service.ConsumerService;
import cn.d1m.pandora.service.LoginService;
import cn.d1m.pandora.utils.CommonConstants;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    static final long EXPIRE_TIME = 1; //1天
    Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    private ConsumerMapper consumerMapper;
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ConsumerService consumerService;

    @Override
    public JSONObject getSession(JSONObject res) {
        if (res != null && !res.has("errcode") && res.has("openid") && res.has("session_key")) {
            // 新增用户，设置session，返回session
            String openid = res.getString("openid");
            String session_key = res.getString("session_key");
            Object unionid = null;
            if (res.has("unionid")) {
                unionid = res.get("unionid");
            }

            String wechatId = null;
            Consumer consumer = consumerService.findByOpenid(openid);
            Consumer insertConsumer = new Consumer();
            if (consumer == null) {

                insertConsumer.setOpenid(openid);
                if (unionid != null) {
                    insertConsumer.setUnionid(unionid.toString());
                }
                consumerMapper.add(insertConsumer);
            } else if (consumer.getUnionid() == null) {
                insertConsumer.setId(consumer.getId());
                if (unionid != null) {
                    insertConsumer.setUnionid(unionid.toString());
                    consumerMapper.updateWithOaurh(insertConsumer);
                }
            } else {
                insertConsumer.setId(consumer.getId());
            }
            String oldKey = redisTemplate.opsForValue().get(CommonConstants.OPENID_PRE + openid);
            if (StringUtils.isNotBlank(oldKey)) {
                redisTemplate.delete(oldKey);
            }
            res.put("id", insertConsumer.getId());
            String sessionId = DigestUtils.md2Hex(openid + session_key);

            if (log.isDebugEnabled()) {
                log.debug("sessionKey:{}, sessionId:{}", session_key, sessionId);
            }

            redisTemplate.opsForValue().set(CommonConstants.SESSION_PRE + sessionId, res.toString(), EXPIRE_TIME, TimeUnit.DAYS);

            System.out.println("oldKey" + oldKey);
            redisTemplate.opsForValue().set(CommonConstants.OPENID_PRE + openid, CommonConstants.SESSION_PRE + sessionId, EXPIRE_TIME, TimeUnit.DAYS);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("token", sessionId);
            return new JSONObject(data);
        }
        return null;
    }
}
