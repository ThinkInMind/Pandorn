package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.dao.SmsSenderMapper;
import cn.d1m.pandora.entry.SmsSender;
import cn.d1m.pandora.service.VerifyCodeService;
import cn.d1m.pandora.utils.URLConstants;
import cn.d1m.pandora.utils.VerifyUtil;
import cn.d1m.pandora.utils.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service("verifyCodeService")
@Slf4j
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SmsSenderMapper smsSenderMapper;

    @Autowired
    private Environment environment;

    @Override
    public boolean sendNewVerifyCode(String mobilePhone) {
        SmsSender smsSender = smsSenderMapper.findByMobilePhone(mobilePhone);
        if (smsSender != null) {
            String verifyCode = smsSender.getVerificationCode();
            if (verifyCode != null) {
                //已经生成过验证码 ，
                int expireTime = environment.getProperty("verifyCode.expireTime", Integer.class) * 60;
                Date dtExpirTime = null;
                try {
                    dtExpirTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(smsSender.getExpireTime());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return false;
                }
                Calendar c = Calendar.getInstance();
                if (dtExpirTime.getTime() - (expireTime - 60) * 1000 <= WXPayUtil.getCurrentTimestampMs()) {
                    //60秒后就可以重发验证码
                    String code = VerifyUtil.get();

                    c.add(Calendar.SECOND, expireTime);
                    //				stringRedisTemplate.expire(mobilePhone, expireTime, TimeUnit.SECONDS);
                    SmsSender sSender = new SmsSender();

                    sSender.setExpireTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime()));
                    sSender.setMobile(mobilePhone);
                    sSender.setVerificationCode(code);
                    sSender.setTimes(0);
                    smsSenderMapper.upateTimesByMobilePhone(sSender);
                    return sendVerifyCode(mobilePhone, code);

                } else {
                    //验证码还在有效期内
                    return false;
                }
            }
        }
        //未生成过验证码
        int expireTime = environment.getProperty("verifyCode.expireTime", Integer.class) * 60;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, expireTime);
        String code = VerifyUtil.get();
        SmsSender sSender = new SmsSender();

        sSender.setExpireTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime()));
        sSender.setMobile(mobilePhone);
        sSender.setVerificationCode(code);
        sSender.setTimes(0);

        smsSenderMapper.insertSmsSender(sSender);
//			stringRedisTemplate.opsForValue().set(mobilePhone,code ,expireTime,TimeUnit.SECONDS);
        return sendVerifyCode(mobilePhone, code);

    }

    /**
     * 发送短信
     *
     * @param verifyCode
     */
    public boolean sendVerifyCode(String mobilePhone, String verifyCode) {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
        param.add("userid", environment.getProperty("pandora.SMS.UID"));
        String timstamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        param.add("timestamp", timstamp);
        try {
            param.add("sign", WXPayUtil.MD5(
                    environment.getProperty("pandora.SMS.userName") + environment.getProperty("pandora.SMS.password") + timstamp, true));
        } catch (Exception e) {
            log.error("MD5 生成签名出错", e);
            e.printStackTrace();
        }
        param.add("mobile", mobilePhone);
        param.add("content", "【PANDORA珠宝】您的验证码为：" + verifyCode);
        param.add("sendTime", "");
        param.add("action", "send");
        param.add("extno", "");
//		String rlt ="<?xml version=\"1.0\" encoding=\"utf-8\" ?><returnsms>\r\n" + 
//				" <returnstatus>Success</returnstatus>\r\n" + 
//				" <message>ok</message>\r\n" + 
//				" <remainpoint>95</remainpoint>\r\n" + 
//				" <taskID>8743950</taskID>\r\n" + 
//				" <successCounts>1</successCounts></returnsms>";
        String rlt = restTemplate.postForObject(URLConstants.getSmsUrl(), param, String.class);
        log.info("手机号： " + mobilePhone + ": verifyCode:" + param.get("content") + " rlt:" + rlt);
        try {
            Map<String, String> rltMap = WXPayUtil.xmlToMap(rlt);
            if ("Success".equals(rltMap.get("returnstatus"))) {
                return true;
            } else
                return false;
        } catch (Exception e) {
            log.error("短信返回异常" + rlt, e);
            return false;
        }
    }

    @Override
    public boolean checkVerifyCode(String mobilePhone, String code) {
        SmsSender smsSender = smsSenderMapper.findByMobilePhone(mobilePhone);
//		String verifyCode = stringRedisTemplate.opsForValue().shortUUID(mobilePhone);
        try {
            if (smsSender != null
                    && smsSender.getVerificationCode() != null
                    && code.equals(smsSender.getVerificationCode())
                    && smsSender.getTimes() < 4
                    && (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(smsSender.getExpireTime())).getTime() >= WXPayUtil.getCurrentTimestampMs()) {
                SmsSender sSender1 = new SmsSender();
                sSender1.setMobile(mobilePhone);
                Calendar c = Calendar.getInstance();
                sSender1.setExpireTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime()));
                smsSenderMapper.upateTimesByMobilePhone(sSender1);
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SmsSender sSender1 = new SmsSender();
        sSender1.setMobile(mobilePhone);
        smsSenderMapper.upateTimesByMobilePhone(sSender1);
        return false;
    }

}
