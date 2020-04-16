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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @date 2019/8/21 12:12
 * @author dshuyou
 */
@Configuration
@MapperScan(basePackages = "com.learn.mapper",sqlSessionTemplateRef = "datasourceTemplate")
public class FirstDataSourceConfig {

	@Bean(name = "datasource")
	@ConfigurationProperties("spring.datasource")
	@Primary
	public DataSource dataSource(){
		return new DruidDataSource();
	}

	@Bean(name="tm")
	@Primary
	DataSourceTransactionManager tm1(@Qualifier("datasource") DataSource datasource) {
		DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
		return txm;
	}

	@Bean(name = "sqlsessionFactory")
	@Primary
	public SqlSessionFactory sqlSessionFactory1(@Qualifier("datasource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		// 若通过配置文件实现mybatis的整合，需要设置配置文件的地址
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:com/kjun/mapper3/*.xml"));
		System.out.println("sqlsession");
		return sessionFactoryBean.getObject();
	}

	/**
	 * 创建sqlSessionTemplate
	 */
	@Bean(name = "datasourceTemplate")
	@Primary
	public SqlSessionTemplate sqlSessionTemplate1(
			@Qualifier("sqlsessionFactory") SqlSessionFactory sessionFactory) throws Exception {
		return new SqlSessionTemplate(sessionFactory);
	}
}
