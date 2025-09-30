package com.OrderServiceBootApp.com.OrderServiceBootApp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveDate(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }

    public void getDate(String key){
        redisTemplate.opsForValue().get(key);
    }

    public Object getCustomer(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
