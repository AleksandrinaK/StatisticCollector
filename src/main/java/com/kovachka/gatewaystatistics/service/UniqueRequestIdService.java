package com.kovachka.gatewaystatistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class UniqueRequestIdService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public UniqueRequestIdService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isDuplicateRequest(String requestId) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        boolean isDuplicate = valueOperations.get(requestId) != null;
        if (!isDuplicate) {
            valueOperations.set(requestId, "exists", 5, TimeUnit.MINUTES);
        }
        return isDuplicate;
    }
}

