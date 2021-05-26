package com.sugeladi.buyunju.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisCommand;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.TimeUnit;

/**
 * @author susu
 */
public class RedisLockStore {

    private static final String LOCK_PREFIX = "mutex_lock_";

    private static final byte[] NX          = "NX".getBytes();
    private static final byte[] EX          = "EX".getBytes();

    private StringRedisTemplate redisTemplate;

    private String generateKey(String resource) {
        return LOCK_PREFIX + resource;
    }

    public StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public Boolean lock(String resource, String lockToken, int expireTimeInSecond) {
        try {
            Object result = redisTemplate.execute((RedisCallback<?>) connection -> {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                // SET key value [EX seconds] [PX milliseconds] [NX|XX]
                byte[][] args = { keySerializer.serialize(generateKey(resource)), valueSerializer.serialize(lockToken),
                        NX, EX, String.valueOf(expireTimeInSecond).getBytes() };
                return connection.execute(RedisCommand.SET.toString(), args);
            });
            return true;
        } catch (Throwable e) {
            return false;
        }
    }


    public Boolean unlock(String resource, String lockToken) {
        String value = redisTemplate.opsForValue().get(generateKey(resource));
        if (!StringUtils.equals(value, lockToken)) {
            return false;
        } else {
            try {
                redisTemplate.delete(generateKey(resource));
            } catch (Throwable e) {
                return  false;
            }
        }
        return true;
    }

    public Boolean updateLockExpireTime(String resource, String lockToken, int expireTimeInSecond) {
        String value = redisTemplate.opsForValue().get(generateKey(resource));
        if (!StringUtils.equals(value, lockToken)) {
            return false;
        }
        try {
            redisTemplate.opsForValue().set(generateKey(resource), lockToken, expireTimeInSecond, TimeUnit.SECONDS);
        } catch (Throwable e) {
            return  false;
        }

        return  true;
    }

}

