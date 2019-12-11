package com.learn.service.impl;

import com.learn.common.constant.RedisConstant;
import com.learn.common.constant.ServiceResult;
import com.learn.component.timertask.LegalIdsBloomFilter;
import com.learn.component.datasource.dynamic.DynamicDataSource;
import com.learn.mbg.BaseMapper;
import com.learn.model.IndexSource;
import com.learn.service.DataService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @author dshuyou
 * @date 2019/12/4 15:35
 */
@Service
public class DataServiceImpl implements DataService {
    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);
    @Resource
    private BaseMapper baseMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Cacheable(value = RedisConstant.SELECT_WITH_ID, keyGenerator = "myKeyGenerator")
    public ServiceResult findOne(String table, String primaryKey, String id) {
        System.out.println("查询数据库。。。。。。。。");

        Object database = redisTemplate.opsForHash().get(RedisConstant.DATABASE_TABLE,table);
        if(database == null){
            logger.error("{} has not hashKey:{}",RedisConstant.DATABASE_TABLE,table);
            return ServiceResult.paramsError(table);
        }
        DynamicDataSource.setDataSource(ObjectUtils.toString(database));
        Map<String,Object> params = new HashMap<>();
        params.put("table",table);
        params.put("pk",primaryKey);
        params.put("id",id);
        Map<String,Object> res = baseMapper.selectWithId(params);
        DynamicDataSource.clear();
        if(res == null){return ServiceResult.notContent();}
        return ServiceResult.success(res);
    }

    @Override
    @Cacheable(value = RedisConstant.SELECT_WITH_ID, keyGenerator = "myKeyGenerator")
    public ServiceResult findOne(String database,String table, String primaryKey, String id) {
        System.out.println("查询数据库。。。。。。。。");

        DynamicDataSource.setDataSource(database);
        Map<String,Object> params = new HashMap<>();
        params.put("table",table);
        params.put("pk",primaryKey);
        params.put("id",id);
        Map<String,Object> res = baseMapper.selectWithId(params);
        DynamicDataSource.clear();
        if(res == null){return ServiceResult.notContent();}
        return ServiceResult.success(res);
    }

    @Override
    public List<Map<String, Object>> select(String database, String table) {
        DynamicDataSource.setDataSource(database);
        List<Map<String, Object>> res = baseMapper.select(table);
        DynamicDataSource.clear();
        return res;
    }

    @Override
    public List<IndexSource> selectIndex(String database, String table) {
        DynamicDataSource.setDataSource(database);
        List<IndexSource> res = baseMapper.selectIndex(table);
        DynamicDataSource.clear();
        return res;
    }

    @Override
    @Cacheable(value = "dataservice",keyGenerator = "myKeyGenerator")
    public List<Map<String, Object>> getObjectByBloom(String database,String table, String primaryKey, String id) {
        List<Map<String, Object>> orderList = new ArrayList<>();
        //判断是否为合法id
        if (!LegalIdsBloomFilter.checkLegalId(id)) {
            //不合法id,直接返回为null
            logger.info("return by bloomFilter");
            return null;
        } else {
            //从缓存中获取
            Object redisValue = redisTemplate.opsForValue().get(id);
            //判断object是否为空（***注意***）
            if (StringUtils.isNotBlank(ObjectUtils.toString(redisValue))) {
                logger.info("from Redis");
                orderList.add((Map<String, Object>) redisValue);
            } else {
                //查询数据库
                logger.info("from DB");
                DynamicDataSource.setDataSource("database1");
                Map<String,Object> params = new HashMap<>();
                params.put("table",table);
                params.put("pk",primaryKey);
                params.put("id",id);
                Map<String, Object> order = baseMapper.selectWithId(params);
                orderList.add(order);
                DynamicDataSource.clear();
                //存入redis
                redisTemplate.opsForValue().set(id, order, 3L, TimeUnit.MINUTES);
            }
        }
        return orderList;
    }
}
