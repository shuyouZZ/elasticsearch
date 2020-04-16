/*
package com.learn.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

*/
/**
 * @author dshuyou
 * @date 2019/12/31 11:42
 *//*

@RunWith(SpringRunner.class)
@SpringBootTest
public class Concurrent2EsServiceTest {
    @Autowired
    private Concurrent2EsService concurrent2EsService;

    @Test
    public void concurrent2Es() throws ExecutionException, InterruptedException {
        String database = "database1";
        String table = "comment_copy";
        String index = "wuhan-test1";
        concurrent2EsService.concurrent2Es(database,table,index);
    }

    @Test
    public void concurrent2File() throws ExecutionException, InterruptedException {
        String database = "database1";
        String table = "comment_copy";
        String file = "D:\\1.txt";
        concurrent2EsService.concurrent2File(database,table,file);
    }
}*/
