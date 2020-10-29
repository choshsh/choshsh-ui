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
@MapperScan(sqlSessionFactoryRef = "db7SqlSessionFactory")
public class DB7Config {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplicationContext applicationContext;

	@Bean(name = "db7DataSource")
	@ConfigurationProperties(prefix = "spring.db7.datasource")
	public DataSource dataSource() {
		DataSource dataSource = DataSourceBuilder.create().build();
		logger.info("datasource : {}", dataSource);
		return dataSource;
	}


	@Bean(name = "db7SqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("db7DataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/*.xml"));
		sqlSessionFactoryBean.setConfigLocation(
				new PathMatchingResourcePatternResolver().getResource("classpath:/config/mybatis-config.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "db7SqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(
			@Qualifier("db7SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
