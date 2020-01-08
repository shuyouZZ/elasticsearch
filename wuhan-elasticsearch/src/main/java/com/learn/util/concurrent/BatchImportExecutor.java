package com.learn.util.concurrent;

import com.alibaba.fastjson.JSONObject;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.model.IndexSource;
import com.learn.service.ElasticsearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dshuyou
 * @date 2019/12/30 16:19
 */
public class BatchImportExecutor implements Runnable{
    private final Logger logger = LoggerFactory.getLogger(BatchImportExecutor.class);
    private String index;
    private List<IndexSource> importData;
    private ElasticsearchService elasticsearchService;

    public BatchImportExecutor(String index, List<IndexSource> importData, ElasticsearchService elasticsearchService){
        this.index = index;
        this.importData = importData;
        this.elasticsearchService = elasticsearchService;
    }

    @Override
    public void run() {
        if(importData == null){
            return;
        }
        List<SourceEntity> bulk = new ArrayList<>();
        for (IndexSource r : importData) {
            SourceEntity sourceEntity = new SourceEntity();
            sourceEntity.setSource(JSONObject.toJSONString(r));
            sourceEntity.setId(r.getId());
            bulk.add(sourceEntity);
        }
        elasticsearchService.bulkIndex(index,bulk);
    }
}
