package com.lh.ekbase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class SpringbootRedisApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;
    public static Logger logger= LoggerFactory.getLogger(SpringbootRedisApplicationTests.class);
    @RequestMapping("/redisTest")
    public String redisTest() {
        try {
            redisTemplate.opsForValue().set("test-key", "redis测试内容", 2, TimeUnit.SECONDS);// 缓存有效期2秒

            logger.info("从Redis中读取数据：" + redisTemplate.opsForValue().get("test-key").toString());

            TimeUnit.SECONDS.sleep(3);

            logger.info("等待3秒后尝试读取过期的数据：" + redisTemplate.opsForValue().get("test-key"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "OK";
    }
}