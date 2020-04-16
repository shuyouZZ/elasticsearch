package com.learn.service.impl;

import com.learn.service.RedisService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @author dshuyou
 * @date 2019/12/11 14:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HotWordServiceImplTest {
    @Autowired
    private RedisService hotWordService;
    private String key;
    private String value;
    private String value1;
    @Before
    public void before(){
        key = "asd";
        value = "def";
        value1 = "ghi";
    }

    @Test
    public void set() {
        long res = hotWordService.set(key,value1);
        System.out.println(res);
    }


    @Test
    public void zset() {
        boolean res = hotWordService.zset(key,value,1);
        System.out.println(res);
    }

    @Test
    public void hasKey() {
        boolean res = hotWordService.hasKey(key);
        System.out.println(res);
    }

    @Test
    public void getHotWord() {
        Set<String> set = hotWordService.getHotWord(key,0,10);
        for (String s : set){
            System.out.println(s);
        }
    }

    @Test
    public void remove() {
    }

    @Test
    public void removeRange() {

    }

    @Test
    public void expire() {
        boolean res = hotWordService.expire(key,120);
        System.out.println(res);
    }

    @Test
    public void increment() {
    }
}