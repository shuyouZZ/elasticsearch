package com.learn.service;

import com.learn.common.constant.ServiceResult;
import com.learn.model.IndexSource;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/11/25 14:49
 */
public interface DataService {
    ServiceResult findOne(String table, String primaryKey, String id);

    ServiceResult findOne(String database, String table, String primaryKey, String id);

    List<Map<String, Object>> select(String database, String table);

    List<IndexSource> selectIndex(String database, String table);

    List<Map<String, Object>> getObjectByBloom(String database,String table, String primaryKey, String id);
}
