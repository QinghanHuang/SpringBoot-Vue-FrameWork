package com.clement.redis_test;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Author Qinghan Huang
 * @Date 2023/6/26 21:56
 * @Desc
 * @Version 1.0
 */
@SpringBootTest
public class RedisTest {
    @Resource
    StringRedisTemplate template;
    @Test
    public void test(){
        template.opsForValue().set("qqq","test");
    }
}
