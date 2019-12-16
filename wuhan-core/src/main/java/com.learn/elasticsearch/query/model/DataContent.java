package com.learn.elasticsearch.query.model;

import java.io.Serializable;
import java.util.List;

/**
 * Used to facilitate the return of query data
 *
 * @author dshuyou
 * @date 2019/11/4 23:00
 */
public class DataContent implements Serializable {
    /**
     * 元数据表
     */
   private String type;
    /**
     * 结果集
     */
   private Object result;
    /**
     * 查询结果总数
     */
   private long count;

    public DataContent() {
    }

    public DataContent(Object result, long count) {
        this.result = result;
        this.count = count;
    }

    public DataContent(String type, Object result, long count) {
        this.type = type;
        this.result = result;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DataContent{" +
                "type='" + type + '\'' +
                ", result=" + result +
                ", count=" + count +
                '}';
    }
}
