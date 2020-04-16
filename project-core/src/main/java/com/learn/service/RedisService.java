package com.learn.service;

import java.util.Map;
import java.util.Set;

/**
 * @author dshuyou
 * @date 2019/10/21 16:38
 */
public interface RedisService {
	void hashPut(String key, String hashkey, String value);

	Long set(String key, String... value);

	Boolean zset(String key, String value, double score);

	Boolean hasKey(String key);

	Boolean hasHashKey(String key, String hashKey);

	String getHashValue(String key, String hashKey);

	Map getHashEntries(String key);

	Set<String> getHotWord(String key,long start, long end);

	Boolean remove(String key);

	Long removeRange(String key, long start, long end);

	Boolean expire(String key, long expire);

	Double increment(String key, String value, long delta);

}
