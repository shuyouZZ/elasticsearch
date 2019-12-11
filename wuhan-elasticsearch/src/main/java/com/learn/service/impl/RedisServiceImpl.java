package com.learn.service.impl;

import com.learn.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author dshuyou
 * @date 2019/10/21 17:40
 */
@Service("RedisServiceImpl")
public class RedisServiceImpl implements RedisService {
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public Long set(String key, String... value) {
		return redisTemplate.opsForSet().add(key,value);
	}

	@Override
	public Boolean zset(String key, String value, double score) {
		return redisTemplate.opsForZSet().add(key,value,score);
	}

	@Override
	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public Set<String> getHotWord(String key, long start, long end) {
		return redisTemplate.opsForZSet().reverseRange(key,start,end);
	}

	@Override
	public Boolean remove(String key) {
		return redisTemplate.delete(key);
	}

	@Override
	public Long removeRange(String key, long start, long end) {
		return redisTemplate.opsForZSet().removeRange(key,start,end);
	}

	@Override
	public Boolean expire(String key, long expire) {
		return redisTemplate.expire(key,expire,TimeUnit.SECONDS);
	}

	@Override
	public Double increment(String key, String value, long delta) {
		return redisTemplate.opsForZSet().incrementScore(key,value,delta);
	}
}
