package com.learn.component.timertask;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.learn.component.datasource.dynamic.DynamicDataSource;
import com.learn.mbg.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/12/11 11:50
 */
@Configuration
@EnableScheduling //开启定时器
public class LegalIdsBloomFilter {
    private Logger logger = LoggerFactory.getLogger(LegalIdsBloomFilter.class);
    private final static int CAPACITY = 1000000;
    private static final BloomFilter<String> BLOOM_FILTER = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), CAPACITY);

    @Autowired
    private BaseMapper baseMapper;

    @Value("${databaseAndTable}")
    private String databaseAndTable;

    /**
     * 开启定时任务,将合法id存入过滤器
     * 每10分钟执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void initLegalIdsBloomFilter() {
        logger.info("................定时调度任务-配置布隆过滤器...............");
        String[] databaseAndTables = databaseAndTable.split(";");
        for (String databaseAndTable : databaseAndTables){
            String[] flag = databaseAndTable.split(":");
            String database = flag[0];
            String[] tables = flag[1].split(",");
            for (String table : tables){
                DynamicDataSource.setDataSource(database);
                List<Map<String, Object>> orders = baseMapper.select(table);
                logger.info("数据库:{},数据表:{},数据量:{}",database,table,orders.size());
                DynamicDataSource.clear();
                orders.forEach(order -> BLOOM_FILTER.put(String.valueOf(order.get("comment_id"))));
            }
        }
    }

    /**
     * 布隆过滤器匹配
     * @param id
     * @return
     */
    public static boolean checkLegalId(String id) {
        return BLOOM_FILTER.mightContain(id);
    }
}