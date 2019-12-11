package com.learn.mbg.mapper4;

import com.learn.mbg.mapper1.ViewMapper1;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewMapperTest {

    @Autowired
    private ViewMapper1 viewMapper1;
    @Test
    public void findOne() {
        String table = "comments";
        String pk = "11";
        Map<String,Object> map =  viewMapper1.findOne(table,pk);
        System.out.println(map);
    }

    @Test
    public void findByPage() {
        String table = "comments";
        String pk = "comment_id";
        int currPage = 0;
        int size = 1000;
        List<Map<String,Object>> list =  viewMapper1.findByPage(table,currPage,size);
        System.out.println(list.size());

    }

    @Test
    public void findByFetch() {
        String table = "comments";
        List<Map<String,Object>> list =  viewMapper1.findByFetch(table);
        System.out.println(list.size());
    }

    @Test
    public void compare() {
        String table = "comment_copy";
        String pk = "id";
        int size = 10000;

        long start = System.currentTimeMillis();
        int count = viewMapper1.count(table,pk);
        int t = count/size;
        int sum = 0;
        for (int i = 0;i < t; i++){
            List<Map<String,Object>> list =  viewMapper1.findByPage(table,i*size,size);
            sum += list.size();
        }
        List<Map<String,Object>> list =  viewMapper1.findByPage(table,t*size,count - t*size);
        long end = System.currentTimeMillis();
        sum += list.size();

        long start1 = System.currentTimeMillis();
        List<Map<String,Object>> list1 =  viewMapper1.findByFetch(table);
        long end1 = System.currentTimeMillis();

        System.out.println("list time: " + (end-start) + "ms");
        System.out.println("list1 time: " + (end1-start1) + "ms");
        System.out.println("list.size：" + sum + " list1.size: " + list1.size());

    }

    @Test
    public void add() {
        String table = "comments";
        List<Map<String,Object>> list =  viewMapper1.findByFetch(table);
        System.out.println(list.size());
        for (int i = 0;i< 200;i++){
            int size = viewMapper1.add(list);
            System.out.println(size);
        }
    }

    @Test
    public void findOne1() {
        String table = "comments";
        Map<String,Object> map = new HashMap<>();
        map.put("table",table);
        map.put("pk","comment_id");
        map.put("id","11");
        Map<String,Object> list =  viewMapper1.selectWithId(map);
        System.out.println(list);
    }
}