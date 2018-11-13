package com.d1m.social.wechat.pandora.integration.layer.api.aop;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.d1m.social.wechat.pandora.integration.layer.api.model.GuestNormalProfile;
import com.d1m.social.wechat.pandora.integration.layer.api.service.GuestNormalProfileService;

@Aspect
@Component
public class TokenAOP {
	
	private final static Logger log = LoggerFactory.getLogger(TokenAOP.class);
	
	@Autowired
	private GuestNormalProfileService guestNormalProfileService;

	/**
	 * 拦截所有controller请求
	 */
	@Pointcut("execution(* com.d1m.social.wechat.card.api.controller.*.*(..))")
	private void method() { }
	
	@Around("method()")
	public String aroundAop(ProceedingJoinPoint pjp) throws Throwable {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		log.info("入参>>>>");
		logRequest(request);
		String token = request.getHeader("token");
		if(StringUtils.isEmpty(token)) {
			log.error("token is null!");
			return "token is null!";
		} else {
			boolean pass = false;
			//根据不同token获取对应用户的profile信息 查询token默认缓存5分钟，token生成规则为前缀: normal, vip , fastpass ，后缀为id
			
			//根据token获取normal用户信息
			if(token.indexOf("normal") >= 0) {
				GuestNormalProfile query = new GuestNormalProfile();
				query.setId(token);
				GuestNormalProfile guestNormalProfile = guestNormalProfileService.searchToken(query);
				if(guestNormalProfile != null) {
					pass = true;
				}
			} 
			//根据token获取vip用户信息
			else if(token.indexOf("vip") >= 0) {
				
			} 
			//根据token获取fastpass用户信息
			else if(token.indexOf("fastpass") >= 0) {
				
			} else {
				pass = false;
			}
			if(pass) {
				Object obj = pjp.proceed();
				log.info("proceed>>" + obj);
				return "successful";
			} else {
				return "fail";
			}
		}
	}
	
	public static void logRequest(HttpServletRequest request) {
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()) {
			String key = params.nextElement();
			log.info(key + ">>>" + request.getParameter(key));
		}
	}
}
