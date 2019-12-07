package com.learn.service.impl;

import com.learn.component.datasource.dynamic.DynamicDataSource;
import com.learn.mbg.BaseMapper;
import com.learn.model.IndexSource;
import com.learn.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author dshuyou
 * @date 2019/12/4 15:35
 */
@Service
public class DataServiceImpl implements DataService {
    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);
    @Resource
    private BaseMapper baseMapper;

    @Override
    public Map<String, Object> findOne(String database,String table, String primaryKey, String id) {
        DynamicDataSource.setDataSource(database);
        Map<String,Object> params = new HashMap<>();
        params.put("table",table);
        params.put("pk",primaryKey);
        params.put("id",id);
        Map<String,Object> res = baseMapper.selectWithId(params);
        DynamicDataSource.clear();
        return res;
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
}
