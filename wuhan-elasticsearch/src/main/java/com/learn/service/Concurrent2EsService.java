/*
package com.learn.service;

import com.learn.common.constant.ServiceResult;
import com.learn.component.datasource.dynamic.DynamicDataSource;
import com.learn.mbg.BaseMapper;
import com.learn.model.IndexSource;
import com.learn.util.concurrent.BatchImportExecutor;
import com.learn.util.concurrent.BatchImportFileExecutor;
import com.learn.util.concurrent.BatchSelectExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

*/
/**
 * @author dshuyou
 * @date 2019/12/30 17:16
 *//*

@Service
public class Concurrent2EsService {
    private final Logger logger = LoggerFactory.getLogger(Concurrent2EsService.class);
    private int pageSize = 50000;
    @Autowired
    private BaseMapper baseMapper;
    @Autowired
    private ElasticsearchService elasticsearchService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    public ServiceResult concurrent2Es(String database, String table, String index) throws ExecutionException, InterruptedException {
        DynamicDataSource.setDataSource(database);
        long count = baseMapper.count(table);
        logger.info("数据表数据量：{}",count);

        for (int i = 0; i < count; i += pageSize) {
            Future f;
            if (i + pageSize > count) {
                f =  taskExecutor.submit(new BatchSelectExecutor(table, i, (int) (count - pageSize), baseMapper));
            } else {
                f = taskExecutor.submit(new BatchSelectExecutor(table, i, pageSize, baseMapper));
            }
            List<IndexSource> result = (List<IndexSource>) f.get();
            logger.info("本次数据量：{}",result.size());
            taskExecutor.execute(new BatchImportExecutor(index,result,elasticsearchService));
        }
        DynamicDataSource.clear();
        return ServiceResult.success("导入成功");
    }

    public ServiceResult concurrent2File(String database, String table, String file) throws ExecutionException, InterruptedException {
        DynamicDataSource.setDataSource(database);
        long count = baseMapper.count(table);
        logger.info("数据表数据量：{}",count);

        for (int i = 0; i < count; i += pageSize) {
            Future f;
            if (i + pageSize > count) {
                f =  taskExecutor.submit(new BatchSelectExecutor(table, i, (int) (count - pageSize), baseMapper));
            } else {
                f = taskExecutor.submit(new BatchSelectExecutor(table, i, pageSize, baseMapper));
            }
            List<IndexSource> result = (List<IndexSource>) f.get();
            logger.info("本次数据量：{}",result.size());
            taskExecutor.execute(new BatchImportFileExecutor(file,result));
        }
        DynamicDataSource.clear();
        return ServiceResult.success("导入成功");
    }
}
*/
