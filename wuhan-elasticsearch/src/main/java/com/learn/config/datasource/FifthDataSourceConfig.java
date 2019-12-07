package com.learn.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author dshuyou
 * @date 2019/11/4 18:54
 */
@Configuration
@MapperScan(basePackages = "com.learn.mbg.mapper5",sqlSessionTemplateRef = "datasourceTemplate5")
public class FifthDataSourceConfig {
    @Bean(name = "datasource5")
    @ConfigurationProperties("spring.datasource.database5")
    public DataSource dataSource5(){
        return new DruidDataSource();
    }


    @Bean(name="tm5")
    DataSourceTransactionManager tm5(@Qualifier("datasource5") DataSource datasource) {
        DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
        return txm;
    }

    @Bean(name = "sqlsessionFactory05")
    public SqlSessionFactory sqlSessionFactory5(@Qualifier("datasource5") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        // 若通过配置文件实现mybatis的整合，需要设置配置文件的地址
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:com/kjun/mapper3/*.xml"));
        System.out.println("sqlsession05");
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "datasourceTemplate5")
    public SqlSessionTemplate sqlSessionTemplate5(
            @Qualifier("sqlsessionFactory05") SqlSessionFactory sessionFactory) throws Exception {
        return new SqlSessionTemplate(sessionFactory);
    }
}
