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
@MapperScan(basePackages = "com.learn.mbg.mapper4",sqlSessionTemplateRef = "datasourceTemplate4")
public class FouthDataSourceConfig {
    @Bean(name = "datasource4")
    @ConfigurationProperties("spring.datasource.database4")
    public DataSource dataSource4(){
        return new DruidDataSource();
    }


    @Bean(name="tm4")
    DataSourceTransactionManager tm4(@Qualifier("datasource4") DataSource datasource) {
        DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
        return txm;
    }

    @Bean(name = "sqlsessionFactory04")
    public SqlSessionFactory sqlSessionFactory4(@Qualifier("datasource4") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        // 若通过配置文件实现mybatis的整合，需要设置配置文件的地址
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:com/kjun/mapper3/*.xml"));
        System.out.println("sqlsession04");
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "datasourceTemplate4")
    public SqlSessionTemplate sqlSessionTemplate4(
            @Qualifier("sqlsessionFactory04") SqlSessionFactory sessionFactory) throws Exception {
        return new SqlSessionTemplate(sessionFactory);
    }
}
