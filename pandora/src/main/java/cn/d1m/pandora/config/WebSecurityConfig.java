package cn.d1m.pandora.config;

import cn.d1m.pandora.srping.SecurityInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Slf4j
public class WebSecurityConfig extends WebMvcConfigurerAdapter {
    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        // 排除配置
        addInterceptor.excludePathPatterns("/api/card/**");
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/onLogin/**");
        addInterceptor.excludePathPatterns("/api/login");

        addInterceptor.excludePathPatterns(
                "/test/**",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/webjars/**");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }


    /**
     * 自动注册到作为http消息转换器，详情看一下源码
     * {@link org.springframework.boot.autoconfigure.web.JacksonHttpMessageConvertersConfiguration}
     * {@link org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration}
     *
     * @param objectMapperProvider json转换lib，bean源码如下
     *                             {@link org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.JacksonObjectMapperConfiguration#jacksonObjectMapper}
     * @return jsonHttpMessageConverter
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectProvider<ObjectMapper> objectMapperProvider) {
        ObjectMapper objectMapper;
        try {
            objectMapper = objectMapperProvider.getIfAvailable();
        } catch (BeansException e) {
            objectMapper = new ObjectMapper();
            log.info("ObjectMapper instance not fund, use a new one!");
        }
        final MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        jsonMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, new MediaType("application", "*+json")));
        return jsonMessageConverter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
