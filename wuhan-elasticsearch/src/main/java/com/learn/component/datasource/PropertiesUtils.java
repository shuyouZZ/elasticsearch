package com.learn.component.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.learn.component.datasource.dynamic.DataSourceBean;
import com.learn.component.datasource.dynamic.DynamicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author dshuyou
 * @date 2019/12/5 12:55
 */
public class PropertiesUtils {
    private static final String DB_DATABASE = ".database";
    private static final String DB_URL = ".url";
    private static final String DB_USERNAME = ".username";
    private static final String DB_PASSWORD = ".password";
    private static final String DB_DRIVER_CLASSNAME = ".driverClassName";
    private static final Object DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";

    public static DruidDataSource initDynamicDatasource(String dataSource){
        DataSourceBean dataSourceBean = praseProperties(dataSource);
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataSourceBean.getUrl());
        druidDataSource.setUsername(dataSourceBean.getUsername());
        druidDataSource.setPassword(dataSourceBean.getPassword());
        druidDataSource.setDriverClassName(dataSourceBean.getDriverClassName());
        druidDataSource.setMaxActive(20);
        druidDataSource.setInitialSize(5);
        //druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        return druidDataSource;
    }

   /* public static void setDatasource(DataSourceBean dataSource){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataSource.getUrl());
        druidDataSource.setUsername(dataSource.getUsername());
        druidDataSource.setPassword(dataSource.getPassword());
        druidDataSource.setDriverClassName(dataSource.getDriverClassName());
        DynamicDataSource.dataSourcesMap.put(dataSource.getBeanName(), druidDataSource);
        DynamicDataSource.setDataSource(dataSource.getBeanName());
    }

    private static DataSource buildDataSource(DataSourceBean dataSource){
        String driverClassName = dataSource.getDriverClassName();
        String url = dataSource.getUrl();
        String username = dataSource.getUsername();
        String password = dataSource.getPassword();

        Class<? extends DataSource> dataSourceType = null;
        try {
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) DATASOURCE_TYPE_DEFAULT);
            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public static DataSourceBean praseProperties(String dataSource){
        InputStream is = PropertiesUtils.class.getClassLoader().getResourceAsStream("config/datasource.properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("utf-8")));
        Properties properties = new Properties();
        try {
            properties.load(br);
            String database = properties.getProperty(dataSource.concat(DB_DATABASE));
            String url = properties.getProperty(dataSource.concat(DB_URL));
            String username = properties.getProperty(dataSource.concat(DB_USERNAME));
            String password = properties.getProperty(dataSource.concat(DB_PASSWORD));
            String driverClassName = properties.getProperty(dataSource.concat(DB_DRIVER_CLASSNAME));

            return new DataSourceBean(new DataSourceBean.DataSourceBeanBuilder(
                    database,database,driverClassName,url,username,password));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}