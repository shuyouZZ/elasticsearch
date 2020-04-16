package com.learn.util.concurrent;

import com.learn.mbg.BaseMapper;
import com.learn.model.IndexSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * When excellence becomes the flow of blood in the body,
 * and the surrounding environment changes again, excellent people will always shine.
 *
 * @author dshuyou
 * @date 2019/12/30 16:16
 */
public class BatchSelectExecutor implements Callable {
    private final Logger logger = LoggerFactory.getLogger(BatchSelectExecutor.class);
    private String table;
    private int start;
    private int size;
    private BaseMapper baseMapper;

    public BatchSelectExecutor(String table, int start, int size, BaseMapper baseMapper) {
        this.table = table;
        this.start = start;
        this.size = size;
        this.baseMapper = baseMapper;
    }

    @Override
    public List<IndexSource> call() throws Exception {
        List<IndexSource> resultData;
        try {
            Thread.sleep(1000);
            resultData = baseMapper.selectByPage(table,start,size);
            return resultData;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
