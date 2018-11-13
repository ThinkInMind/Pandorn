package com.d1m.social.wechat.pandora.integration.layer.api.config;

import com.d1m.social.wechat.pandora.integration.layer.api.filter.PayloadLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new PayloadLoggingFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("PayloadLoggingFilter");
        filterRegistrationBean.setOrder(0);
        return filterRegistrationBean;
    }

}
