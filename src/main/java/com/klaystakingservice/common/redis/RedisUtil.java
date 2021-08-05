package com.klaystakingservice.common.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisUtil {

    private final StringRedisTemplate stringRedisTemplate;

    public void setDataExpire(String key,String value,long duration){
        log.debug("==== setDataExpire start ====");
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration / 1000L);
        valueOperations.set(key,value,expireDuration);
    }


    public String getData(String key){
        log.debug("===== getData start ========");
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }
}
