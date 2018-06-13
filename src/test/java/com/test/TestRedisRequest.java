package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;

public class TestRedisRequest {
    static int countTrue = 0;
    static int countFail = 0;
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("root-context.xml");
        JedisPool pool = (JedisPool) ac.getBean("jedisPool");
        RedisRateLimiter limiter = new RedisRateLimiter(pool, TimeUnit.SECONDS, 1);
        for(int i=0;i<100000;i++) {
            Boolean flag = limiter.acquire("aaa");
            if(flag) {
                countTrue++;
            } else {
                countFail++;
            }
        }
        System.out.println("countTrue=" +countTrue);
        System.out.println("countFail=" +countFail);
        System.out.println(limiter.acquire("aaa"));
        System.out.println(limiter.acquire("bbb"));
    }
}
