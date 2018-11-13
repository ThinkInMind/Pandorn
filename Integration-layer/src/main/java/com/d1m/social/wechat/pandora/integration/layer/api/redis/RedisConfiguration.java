//package com.d1m.social.wechat.pandora.integration.layer.api.redis;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import redis.clients.jedis.JedisPoolConfig;
//@Configuration
//@Component("redisConfiguration")
//public class RedisConfiguration {
//
//	@Bean
//	public JedisConnectionFactory jedisConnectionFactory() {
//		JedisConnectionFactory factory = new JedisConnectionFactory();
//		factory.setHostName(System.getProperty("redis.host"));
//		factory.setPort(Integer.valueOf(System.getProperty("redis.port")));
//		factory.setPassword(System.getProperty("redis.password"));
//		factory.setPoolConfig(jedisPoolConfig());
//		factory.setUsePool(true);
//		return factory;
//	}
//	@Bean
//	public JedisPoolConfig jedisPoolConfig() {
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxTotal(Integer.valueOf(System.getProperty("redis.pool.maxTotal")));
//		config.setMaxIdle(Integer.valueOf(System.getProperty("redis.pool.maxIdle")));
//		config.setMaxWaitMillis(Long.valueOf(System.getProperty("redis.pool.maxWaitMillis")));
//		config.setTestOnBorrow(Boolean.valueOf(System.getProperty("redis.pool.testOnBorrow")));
//		return config;
//	}
//	@Bean
//	public RedisTemplate redisTemplate() {
//		RedisTemplate redisTemplate = new RedisTemplate();
//		redisTemplate.setConnectionFactory(jedisConnectionFactory());
//		return redisTemplate;
//	}
//	@Bean
//	public StringRedisTemplate stringRedisTemplate() {
//		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory());
////		stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());
//		return stringRedisTemplate;
//	}
//}
