package com.learn.mbg.mapper1;

import com.learn.component.datasource.dynamic.DynamicDataSource;
import com.learn.mbg.BaseMapper;
import com.learn.service.DataService;
import com.learn.service.ElasticsearchService;
import com.learn.util.concurrent.BatchImportExecutor;
import com.learn.util.concurrent.BatchSelectExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author dshuyou
 * @date 2019/12/30 12:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTestTest {
    @Autowired
    private BaseMapper baseMapper;
    @Autowired
    private DataService dataService;
    @Autowired
    private ElasticsearchService elasticsearchService;
    @Autowired
    private BatchSelectExecutor producer;
    @Autowired
    private BatchImportExecutor consumer;
    @Test
    public void find() {
        String database = "database1";
        DynamicDataSource.setDataSource(database);
        System.out.println(baseMapper.count("comment_copy"));
    }

    @Test
    public void findAll() {
        ExecutorService service1 = Executors.newFixedThreadPool(8);
        //ExecutorService service2 = Executors.newFixedThreadPool(8);
        String database = "database1";
        String table = "comment_copy";
        String index = "test";
        DynamicDataSource.setDataSource(database);
        for (int i = 0;i<5;i++){
            service1.submit(producer);
        }

    }
}