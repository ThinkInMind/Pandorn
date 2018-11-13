package com.d1m.social.wechat.pandora.integration.layer.api.redis;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

import com.d1m.social.wechat.pandora.integration.layer.api.util.ApplicationContextHolder;

public class RedisCache implements Cache {

	private static final Logger LOG = LoggerFactory.getLogger(RedisCache.class);

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final String id; // cache instance id
	private RedisTemplate<Object, Object> redisTemplate;

	public RedisCache(String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		this.id = id;
	}

	/**
	 * @return string
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @return int
	 */
	@Override
	public int getSize() {
		RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
		redisTemplate.setKeySerializer(redisTemplate.getHashKeySerializer());
		HashOperations hashOperations = redisTemplate.opsForHash();
		Map map = hashOperations.entries(id);
		LOG.error("RedisCache getSize>>>id:>>" + id);
		if(map != null && !map.isEmpty()) {
			return map.size();
		} else {
			return 0;
		}
	}

	/**
	 * Put query result to redis
	 *
	 * @param key
	 * @param value
	 */
	@Override
	public void putObject(Object key, Object value) {
		RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
		redisTemplate.setKeySerializer(redisTemplate.getHashKeySerializer());
		
		HashOperations hashOperations = redisTemplate.opsForHash();
//		ValueOperations opsForValue = redisTemplate.opsForValue();
		if (key == null) {
			LOG.error("RedisCache putObject>>>id:>>" + id+">key>>"+key);
		}
		try {
			//增加空判断，如果查询结果为空，不插入缓存
			if(value == null || (value instanceof String && StringUtils.isEmpty((String) value))) {
				LOG.error("RedisCache putObject>>>id:>>" + id+">key>>"+key);
				return;
			}
			LOG.info("RedisCache putObject>>>id:>>" + id+">key>>"+key);
			hashOperations.put(id, key, value);
			//缓存5分钟
			long timeout = 300l;
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * Get cached query result from redis
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Object getObject(Object key) {
		RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
		redisTemplate.setKeySerializer(redisTemplate.getHashKeySerializer());
//		ValueOperations<Object, Object> opsForValue = redisTemplate.opsForValue();
		HashOperations hashOperations = redisTemplate.opsForHash();
		LOG.info("Get cached query result from redis id>>"+id+">key>>"+key);
//		return opsForValue.get(key);
		return hashOperations.get(id, key);
	}

	/**
	 * Remove cached query result from redis
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Object removeObject(Object key) {
		RedisTemplate redisTemplate = getRedisTemplate();
		redisTemplate.setKeySerializer(redisTemplate.getHashKeySerializer());
		HashOperations hashOperations = redisTemplate.opsForHash();
		if (key == null) {
			LOG.error("RedisCache removeObject>>>id:>>" + id+">key>>"+key);
		}
		hashOperations.delete(id, key);
		LOG.info("Remove cached query result from redis>>"+id+">>"+key);
		return null;
	}

	/**
	 * Clears this cache instance
	 */
	@Override
	public void clear() {
		RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
		redisTemplate.setKeySerializer(redisTemplate.getHashKeySerializer());
		final RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer(); 
		redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection conn) {
				byte[] keyByte = redisSerializer.serialize(id);
				conn.del(keyByte);
				LOG.info("clear cached query result from redis"+id);
				return "successful";
			}
		});
	}

	/**
	 * @return ReadWriteLock
	 */
	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

	/**
	 * @return RedisTemplate<String, String>
	 */
	private RedisTemplate<Object, Object> getRedisTemplate() {
		if (redisTemplate == null) {
			redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
		}
		return redisTemplate;
	}
}
