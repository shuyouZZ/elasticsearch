package com.learn.component;

import redis.clients.jedis.Jedis;

/**
 * Jedis singleton class
 * @author dshuyou
 * @date 2019/12/10 11:11
 */
public class JedisUtil {
    private final static String HOST = "localhost";
    private final static int PORT = 6379;
    private volatile static JedisUtil instance;
    public Jedis jedis;

    private JedisUtil(){
        jedis = new Jedis(HOST,PORT);
    }

    public static JedisUtil getInstance(){
        if(instance == null){
            synchronized (JedisUtil.class){
                if(instance == null){
                    return new JedisUtil();
                }
            }
        }
        return instance;
    }
}