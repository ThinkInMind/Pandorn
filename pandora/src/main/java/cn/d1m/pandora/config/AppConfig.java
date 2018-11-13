package cn.d1m.pandora.config;

import cn.d1m.pandora.constant.Constant_;
import cn.d1m.pandora.domain.mybatis.plugin.SqlCastInInterceptor;
import cn.d1m.pandora.filter.PayloadLoggingFilter;
import cn.d1m.pandora.utils.ApplicationContextHolder;
import cn.d1m.pandora.utils.EnvUtils;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.BeansException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig implements EnvironmentAware, ApplicationContextAware {

    /**
     * 增加持久化参数
     *
     * @return mq队列配置
     */
    @Bean
    public Queue crmidQueue() {
        return new Queue(Constant_.PANDORA_CONSUMER_CRMID_QUEUE, true);
    }

    @Bean
    public Queue registQueue() {
        return new Queue(Constant_.PANDORA_REGIST_QUEUE, true);
    }

    @Override
    public void setEnvironment(Environment environment) {
        EnvUtils.setEnvironment(environment);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.setApplicationContext(applicationContext);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new PayloadLoggingFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("PayloadLoggingFilter");
        filterRegistrationBean.setOrder(0);
        return filterRegistrationBean;
    }

    @Bean
    @Profile({"dev", "uat", "local"})
    public SqlCastInInterceptor sqlCastInInterceptor() {
        return new SqlCastInInterceptor();
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        builder.setConnectTimeout(8 * 1000);
        builder.setReadTimeout(10 * 1000);
        return builder.build();
    }
}
