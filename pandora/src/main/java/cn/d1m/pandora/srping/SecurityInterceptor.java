package cn.d1m.pandora.srping;

import cn.d1m.pandora.domain.entity.BusinessBa;
import cn.d1m.pandora.domain.exception.NullTokenAuthenticationException;
import cn.d1m.pandora.domain.wechat.MiniAppSession;
import cn.d1m.pandora.utils.BusinessBaHolder;
import cn.d1m.pandora.utils.CommonConstants;
import cn.d1m.pandora.utils.MiniAppSessionHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

//import com.d1mgroup.wechat.estore.model.Member;
//import com.d1mgroup.wechat.estore.repository.EstoreMemberRepository;
//import com.d1mgroup.wechat.estore.wechatclient.WechatOauthRestService;
//import org.apache.commons.lang3.StringUtils;


/**
 * MemberInterceptor
 *
 * @author f0rb on 2017-09-28.
 */
public class SecurityInterceptor implements HandlerInterceptor {
    static final String TOKEN = "token";
    static final String SESSION_KEY = "sessionKey";
    static final long EXPIRE_TIME = 1; //1天

    private Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        JSONObject memberJSON = this.getSession(request);
        if (memberJSON != null) {
            // miniapp 使用request attribute授权
            String id = memberJSON.getString("id");
            response.setHeader("id", id);
            request.setAttribute("token", id);
//                request.setAttribute(SESSION_KEY,memberJSON.getString("session_key"));
            return true;
        } else {
            BusinessBaHolder.setBusinessBa(getBa(request));
            return true;
        }
//        response.setContentType("application/json");
//        response.getWriter().append(new JSONObject("{\"status\":\"fail\",\"error_info\":\"login first\"}").toString());
//        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
//        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private JSONObject getSession(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        if (token != null) {
            String value = stringRedisTemplate.opsForValue().get(CommonConstants.SESSION_PRE + token);
            if (value != null) {
                try {
                    final MiniAppSession miniAppSession = objectMapper.readValue(value, MiniAppSession.class);
                    if (Objects.nonNull(miniAppSession)) {
                        MiniAppSessionHolder.setMiniAppSession(miniAppSession);
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
                return new JSONObject(value);
            }
        }
        return null;
    }

    private BusinessBa getBa(HttpServletRequest request) {
        final String token = request.getHeader(CommonConstants.X_SESSION_TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new NullTokenAuthenticationException("未授权");
        }
        final BusinessBa ba = (BusinessBa) redisTemplate.opsForValue().get(token);
        if (Objects.isNull(ba)) {
            throw new AccountExpiredException("X-Session-Token已过期");
        }
        return ba;
    }
}
