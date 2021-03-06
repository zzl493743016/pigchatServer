package com.pig.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Arthas
 * @create 2018/11/1
 */
@Component
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static Random random =new Random();

    /**
     * 默认过期时长，单位：秒
     */
    public static final long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * del操作
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * set操作
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * setex操作(有过期时间)
     */
    public void setex(String key, String value, long expireTime) {
        // 过期时间增加随机时间，以免发生同时key过期造成redis卡顿
        expireTime = expireTime * 1000 + random.nextInt(1000) + 1;
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * set操作
     */
    public void set(String key, String value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * get操作
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * hset操作
     */
    public void hset(String key, String hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * hget操作
     */
    public Object hget(String key, String hashKey) {
        Object value = redisTemplate.opsForHash().get(key, hashKey);
        return value;
    }

    /**
     * hdel操作
     */
    public void hdel(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * hgetall操作
     */
    public Map<Object, Object> hgetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * hvals操作
     */
    public List<Object> hvals(String key) {
        return redisTemplate.opsForHash().values(key);
    }
}
