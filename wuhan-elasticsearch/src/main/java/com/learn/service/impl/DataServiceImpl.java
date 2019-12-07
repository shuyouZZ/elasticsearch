package com.learn.service.impl;

import com.learn.common.constant.ServiceResult;
import com.learn.mbg.BaseMapper;
import com.learn.mbg.mapper1.ViewMapper1;
import com.learn.mbg.mapper4.ViewMapper4;
import com.learn.mbg.mapper5.ViewMapper5;
import com.learn.model.IndexSource;
import com.learn.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author dshuyou
 * @date 2019/12/4 15:35
 */
@Service
public class DataServiceImpl implements DataService {
    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);
    @Resource
    private ViewMapper1 viewMapper1;
    @Resource
    private ViewMapper4 viewMapper4;
    @Resource
    private ViewMapper5 viewMapper5;

    @Override
    public Map<String, Object> findOne(String database,String table, String primaryKey, String id) {
        Map<String,Object> params = new HashMap<>();
        params.put("table",table);
        params.put("pk",primaryKey);
        params.put("id",id);
        BaseMapper mapper;
        try {
            mapper = getMapper(getClass(database));
            if(mapper!=null){
                return mapper.selectWithId(params);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> select(String database, String table) {
        BaseMapper mapper;
        try {
            mapper = getMapper(getClass(database));
            if(mapper!=null){
                return mapper.select(table);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<IndexSource> selectIndex(String database, String table) {
        BaseMapper mapper;
        try {
            mapper = getMapper(getClass(database));
            if(mapper!=null){
                return mapper.selectIndex(table);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public BaseMapper getMapper(String className){
        if("".equals(className)){
            logger.error("ClassName Is Null");
        }
        BaseMapper mapper;
        switch (className){
            case "database1":
                mapper = viewMapper1;
                break;
           /* case "database2":
                mapper = viewMapper2;
                break;
            case "database3":
                mapper = viewMapper3;
                break;*/
            case "database4":
                mapper = viewMapper4;
                break;
            case "database5":
                mapper = viewMapper5;
                break;
            default:
                throw new IllegalArgumentException("Not Supported Data Source");
        }
        return mapper;
    }

    private String getClass(String database) throws IOException {
        Properties properties = new Properties();
        ClassPathResource classPathResource = new ClassPathResource("datasource.properties");
        InputStream inputStream =classPathResource.getInputStream();
        properties.load(inputStream);
        return properties.getProperty(database);
    }
}