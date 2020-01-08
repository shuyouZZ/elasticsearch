package com.learn.util.concurrent;

import com.alibaba.fastjson.JSONObject;
import com.learn.model.IndexSource;

import java.io.*;
import java.util.List;

/**
 * @author dshuyou
 * @date 2019/12/31 13:04
 */
public class BatchImportFileExecutor implements Runnable{
    private String file;
    private List<IndexSource> importData;

    public BatchImportFileExecutor(String file, List<IndexSource> importData){
        this.file = file;
        this.importData = importData;
    }

    @Override
    public void run() {
        try {
            try (FileWriter out = new FileWriter(file)) {
                for (IndexSource source : importData) {
                    out.write(JSONObject.toJSONString(source));
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
