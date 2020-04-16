package com.learn.service.impl;

import com.learn.elasticsearch.Document;
import com.learn.elasticsearch.Indice;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.service.AsycService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.learn.elasticsearch.Indice.*;

/**
 * @author dshuyou
 * @date 2019/11/28 9:42
 */
@Service
public class AsycServiceImpl implements AsycService {
    private static final Logger logger = LoggerFactory.getLogger(AsycServiceImpl.class);

    @Async
    @Override
    public void bulkIndex(Document document, Indice indice, String index, List<SourceEntity> source) {
        try {
            //索引数据前调整副本和刷新时间，完成后再更改回来，以提升索引效率和稳定性
            //indice.updateSetting(index,INIT_REPLICAS,String.valueOf(INIT_REFLUSH_INTERVAL));
            indice.updateSetting(index,new Indice.Setting()
                    .setReplicas(INIT_REPLICAS)
                    .setRefresh(String.valueOf(INIT_REFLUSH_INTERVAL))
                    .setTranslog(true));

            logger.info("Start asyc bulk index to {}" , index);
            document.asycBulkIndex(index, source);
        }  catch (InterruptedException | IOException e) {
            logger.error("Asyc bulk index failed {}" ,e.getMessage());
        } finally {
            try {
                //indice.updateSetting(index,REPLICAS,String.valueOf(REFRESH_INTERVAL)+"s");
                indice.updateSetting(index,new Indice.Setting()
                        .setReplicas(REPLICAS)
                        .setRefresh(REFRESH_INTERVAL)
                        .setTranslog(false));
            } catch (IOException e) {
                logger.error("UpdateSetting failed {}" , e.getMessage());
            }
        }
    }

    @Async
    @Override
    public void bulkUpdate(Document document, String index, List<SourceEntity> source) {
        try {
            logger.info("Start asyc bulk update to {}" ,index);
            document.asycBulkUpdate(index, source);
        }  catch (InterruptedException e) {
            logger.error("Asyc bulk update failed {}" , e.getMessage());
        }
    }

    @Async
    @Override
    public void bulkDelete(Document document, String index, List<SourceEntity> source) {
        try {
            logger.info("Start asyc bulk delete to {}" ,index);
            document.asycBulkDelete(index, source);
        }  catch (InterruptedException e) {
            logger.error("Asyc bulk delete failed {}" ,e.getMessage());
        }
    }
}
