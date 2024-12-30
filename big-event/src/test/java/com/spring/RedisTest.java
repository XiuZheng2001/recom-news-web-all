package com.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

// 注解表示单元测试执行之前，先初始化spring容器
@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() {
        // 需要往redis里面存储一个键值对StringRedisTemplate
        // redis的起步依赖会自动注入StringRedisTemplate这个对象
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("username", "zhangsand");
        operations.set("id", "1", 15, TimeUnit.SECONDS);
        String token_redis = operations.get("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjEsInVzZXJuYW1lIjoidyJ9LCJleHAiOjE3MzI2OTQzMjB9.geET9WWXy_3VdsuDMPlZY71LbYXyqIeVQnObzjewXH4");
        System.out.println(token_redis);
    }

    @Test
    public void getTest() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        System.out.println(operations.get("username"));
        System.out.println(operations.get("id"));
    }
}
