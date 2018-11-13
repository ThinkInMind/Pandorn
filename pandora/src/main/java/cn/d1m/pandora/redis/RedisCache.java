package cn.d1m.pandora.redis;

import cn.d1m.pandora.utils.ApplicationContextHolder;
import cn.d1m.pandora.utils.EnvUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@SuppressWarnings("unchecked")
@Slf4j
public class RedisCache implements Cache {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    /**
     * cache instance id
     */
    private final String id;
    private RedisTemplate redisTemplate = null;

    public RedisCache(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        log.info("Init Redis Cache Id: " + id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {

        if (Objects.isNull(key) || Objects.isNull(value)) {
            log.warn("key or value is null!");
            return;
        }
        getRedisTemplate().opsForValue().set(key.toString(), value, EnvUtils.getProperty("redis.data.timeout.min", Long.class, 5L), TimeUnit.MINUTES);

    }

    @Override
    public Object getObject(Object key) {
        try {
            if (key != null) {
                return getRedisTemplate().opsForValue().get(key.toString());
            }
        } catch (Exception e) {
            log.error("redis shortUUID object error:", e);
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        try {
            if (key != null) {
                getRedisTemplate().delete(key.toString());
            }
        } catch (Exception e) {
            log.error("mybatis cache delete error: ", e);
        }
        return null;
    }

    @Override
    public void clear() {
        try {
            final Set keys = getRedisTemplate().keys("*:" + this.id + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                getRedisTemplate().delete(keys);
            }
        } catch (Exception e) {
            log.error("mybatis cache error", e);
        }
    }

    @Override
    public int getSize() {
        Long size = getRedisTemplate().execute(RedisServerCommands::dbSize);
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    private RedisTemplate<Object, Object> getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;
    }

}
