package com.d1m.social.wechat.pandora.integration.layer.api.util;

import org.springframework.util.Assert;

public class CustomerContextHolder {
	
	private static final ThreadLocal<Integer> contextHolder = new ThreadLocal<Integer>();

	public static void setCustomerType(Integer wechatId) {
		Assert.notNull(wechatId, "customerType cannot be null");
		contextHolder.set(wechatId);
	}

	public static Integer getCustomerType() {
		return (Integer) contextHolder.get();
	}

	public static void clearCustomerType() {
		contextHolder.remove();
	}

}
