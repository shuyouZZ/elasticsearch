package com.learn.service.impl;

import com.learn.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author dshuyou
 * @date 2019/12/30 9:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceImplTest {

    @Autowired
    private RedisService redisService;
    @Test
    public void hashPut() {
        redisService.hashPut("1","1","1");
    }

    @Test
    public void set() {
    }

    @Test
    public void zset() {
    }

    @Test
    public void hasKey() {
    }

    @Test
    public void hasHashVlaue() {
        String key = "DATABASE_TABLE";
        String hashKey = "comments";
        boolean res = redisService.hasHashKey(key,hashKey);
        System.out.println(res);
    }

    @Test
    public void getHotWord() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void removeRange() {
    }

    @Test
    public void expire() {
    }

    @Test
    public void increment() {
    }
}