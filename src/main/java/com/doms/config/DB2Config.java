package com.doms.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:/application.yml")
@EnableTransactionManagement
@MapperScan(sqlSessionFactoryRef = "db2SqlSessionFactory")
public class DB2Config {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean(name = "db2DataSource")
	@ConfigurationProperties(prefix = "spring.db2.datasource")
	public DataSource dataSource() {
		DataSource dataSource = DataSourceBuilder.create().build();
		logger.info("datasource : {}", dataSource);
		return dataSource;
	}


	@Bean(name = "db2SqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/*.xml"));
		sqlSessionFactoryBean.setConfigLocation(
				new PathMatchingResourcePatternResolver().getResource("classpath:/config/mybatis-config.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "db2SqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(
			@Qualifier("db2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
