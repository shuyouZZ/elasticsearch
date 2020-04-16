package com.learn.service;

import com.learn.elasticsearch.Document;
import com.learn.elasticsearch.Indice;
import com.learn.elasticsearch.model.SourceEntity;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author dshuyou
 * @date 2019/11/28 9:40
 */
public interface AsycService {
    /**
     * @param document  Document实例
     * @param indice    Indice实例
     * @param index     索引
     * @param source    索引数据
     */
    void bulkIndex(Document document, Indice indice, String index, List<SourceEntity> source);

    /**
     * @param document  Document实例
     * @param index     索引
     * @param source    索引数据
     */
    void bulkUpdate(Document document, String index, List<SourceEntity> source);

    /**
     * @param document  Document实例
     * @param index     索引
     * @param source    索引数据
     */
    void bulkDelete(Document document, String index, List<SourceEntity> source);
}
