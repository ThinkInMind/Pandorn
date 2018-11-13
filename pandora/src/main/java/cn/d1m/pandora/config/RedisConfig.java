package cn.d1m.pandora.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;

/**
 * Created by jone.wang on 2018/9/13.
 * Description:
 */
@Configuration
public class RedisConfig {
    private final RedisTemplate redisTemplate;

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisConfig(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        stringRedisTemplate.setKeySerializer(stringRedisSerializer());
        stringRedisTemplate.setHashKeySerializer(stringRedisSerializer());
        stringRedisTemplate.setValueSerializer(stringRedisSerializer());
        stringRedisTemplate.setHashValueSerializer(jsonRedisSerializer());

        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(jsonRedisSerializer());
        redisTemplate.setHashKeySerializer(stringRedisSerializer());
        redisTemplate.setHashValueSerializer(jsonRedisSerializer());
    }

    @Bean("jsonRedisSerializer")
    public RedisSerializer jsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean("stringRedisSerializer")
    public RedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }


}
