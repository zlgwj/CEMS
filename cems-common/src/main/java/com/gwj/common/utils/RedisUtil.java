package com.gwj.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtil {


    //    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(RedisSerializer.json());
        this.redisTemplate.setValueSerializer(RedisSerializer.json());
    }


    /**
     * 写入缓存
     * &#64;param key
     * &#64;param value
     * &#64;return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("设置{},有误", key);
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     * &#64;param key
     * &#64;param value
     * &#64;return
     */
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 批量删除对应的value
     * &#64;param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * &#64;param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     * &#64;param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * &#64;param key
     * &#64;return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     * &#64;param key
     * &#64;return
     */
    public Object get(final String key) {
        Object result;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     * &#64;param key
     * &#64;param hashKey
     * &#64;param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     * &#64;param key
     * &#64;param hashKey
     * &#64;return
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     * &#64;param k
     * &#64;param v
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     * &#64;param k
     * &#64;param l
     * &#64;param l1
     * &#64;return
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     * &#64;param key
     * &#64;param value
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     * &#64;param key
     * &#64;return
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * &#64;param key
     * &#64;param value
     * &#64;param scoure
     */
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * 有序集合获取
     * &#64;param key
     * &#64;param scoure
     * &#64;param scoure1
     * &#64;return
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    /**
     *      * 删除redis缓存
     *      * @param key
     *      * @return
     *     
     */
    public void delete(String key) {
        if (!StringUtils.isEmpty(key)) {
            redisTemplate.delete(key);
        }
    }

    public void insert(String key, String value, long timeout, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(key) || ObjectUtils.isEmpty(value)) {
            return;
        }
        if (timeout == 0) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        }
    }

    public void putHash(String key, String hashKey, String value, long timeout, TimeUnit timeUnit) {
        HashOperations<String, Object, Object> opHash = redisTemplate.opsForHash();
        opHash.put(key, hashKey, value);
        //设置超时时间24小时 第三个参数控制时间单位，详情查看TimeUnit
        redisTemplate.expire(key, timeout, timeUnit);
    }

    public Set<String> scan(String pattern) {
        Set<String> keySet;
        keySet = Collections.unmodifiableSet((Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTemp = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(pattern).count(10000).build());
            while (cursor.hasNext()) {
                keysTemp.add(new String(cursor.next()));
            }
            return keysTemp;
        }));
        return keySet;
    }

    public void removeBatch(String pattern) {
        Set<String> keys = scan(pattern);
        for (String key : keys) {
            remove(key);
        }
    }
}
