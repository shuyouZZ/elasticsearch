package com.learn.component;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Redis distributed lock
 */
public class RedisDistributedLock implements Lock {
//    private final static String HOST = "localhost";
//    private final static int PORT = 6379;
    private final static String KEY = "redis";
    private final static long TIME = 100;
    private ThreadLocal<String> lockContext = new ThreadLocal<String>();
    private Thread exclusiveOwnerThread ;

    public RedisDistributedLock(){}

    @Override
    public void lock() {
        while (!tryLock()){
            try {
                Thread.sleep(TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        if(Thread.interrupted()){
            throw new InterruptedException();
        }
        while (!tryLock()){
            Thread.sleep(TIME);
        }
    }

    @Override
    public boolean tryLock() {
       return tryLock(TIME,TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        String id = UUID.randomUUID().toString();
        Thread t = Thread.currentThread();
        Jedis jedis = JedisUtil.getInstance().jedis;
        if(jedis.setnx(KEY,id) == 1){
            jedis.pexpire(KEY, unit.toMillis(time));
            lockContext.set(id);
            setExclusiveOwnerThread(t);
            return true;
        }else if(exclusiveOwnerThread == t){
            return true;
        }
        return false;
    }

    @Override
    public void unlock() {
        String script = null;
        try{
            Jedis jedis = JedisUtil.getInstance().jedis;
            script = inputStream2String(getClass().getResourceAsStream("/redis.script"));
            if(lockContext.get() == null){
                return ;
            }
            jedis.eval( script, Collections.singletonList(KEY), Collections.singletonList(lockContext.get()));
            lockContext.remove();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        // TODO Auto-generated method stu
        return null;
    }

    private void setExclusiveOwnerThread(Thread t){
        this.exclusiveOwnerThread = t;
    }

    private String inputStream2String (InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[1024];
        for(int n;(n=in.read(b))!=-1;){
            out.append(new String(b,n));
        }
        return out.toString();
    }
}