package com.d1m.social.wechat.pandora.integration.layer.api;

import com.d1m.social.wechat.pandora.integration.layer.api.util.EnvUtils;
import org.apache.commons.io.FileUtils;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * 潘多拉小程序项目,scv,gigiya接口整合项目
 * 微信tag微服务
 */
@RestController
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.d1m.social.wechat.pandora.integration.layer.api"},
        exclude = {DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class, RabbitAutoConfiguration.class})
public class IntegrationLayerApplication implements EnvironmentAware {

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/redisSet")
    public String redisSet(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        return "RedisSet successful!";
    }

    @RequestMapping("/redisGet")
    public String redisGet(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    public static void main(String[] args) throws IOException {
        final InputStream inputStream = IntegrationLayerApplication.class
                .getClassLoader()
                .getResourceAsStream("log4j2.xml");
        final File file = new File("logs/log4j2.xml");
        FileUtils.copyInputStreamToFile(inputStream, file);
        SpringApplication.run(IntegrationLayerApplication.class, args);
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5 * 1000);
        factory.setReadTimeout(5 * 1000);
        return factory;
    }

    @Bean("restHttpTemplate")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(httpComponentsClientHttpRequestFactory());
        return restTemplate;
    }

    @Override
    public void setEnvironment(Environment environment) {
        EnvUtils.setEnvironment(environment);
    }
}