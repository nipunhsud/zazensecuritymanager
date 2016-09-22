package com.securitymanager.v1.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

@Configuration
@ComponentScan(basePackages="com.securitymanager")
@PropertySource( "classpath:application.properties")
@MapperScan("com.securitymanager.v1.dao")
@EnableWebMvc
public class SecurityManagerServiceConfiguration  extends WebMvcConfigurerAdapter {
	@Value("${mysql.jdbc.driver.name}")
	private String jdbcDriverName;
	
	@Value("${mysql.jdbc.connection.url}")
	private String jdbcConnectionUrl;
	
	@Value("${mysql.jdbc.database.username}")
	private String jdbcDatabaseUsername;
	
	@Value("${mysql.jdbc.database.password}")
	private String jdbcDatabasePassword;
	
	public String getJdbcDriverName() {
		return jdbcDriverName;
	}

	public void setJdbcDriverName(String jdbcDriverName) {
		this.jdbcDriverName = jdbcDriverName;
	}

	public String getJdbcConnectionUrl() {
		return jdbcConnectionUrl;
	}

	public void setJdbcConnectionUrl(String jdbcConnectionUrl) {
		this.jdbcConnectionUrl = jdbcConnectionUrl;
	}

	public String getJdbcDatabaseUsername() {
		return jdbcDatabaseUsername;
	}

	public void setJdbcDatabaseUsername(String jdbcDatabaseUsername) {
		this.jdbcDatabaseUsername = jdbcDatabaseUsername;
	}

	public String getJdbcDatabasePassword() {
		return jdbcDatabasePassword;
	}

	public void setJdbcDatabasePassword(String jdbcDatabasePassword) {
		this.jdbcDatabasePassword = jdbcDatabasePassword;
	}

	public String getJdbcDatabaseConnectionPool() {
		return jdbcDatabaseConnectionPool;
	}

	public void setJdbcDatabaseConnectionPool(String jdbcDatabaseConnectionPool) {
		this.jdbcDatabaseConnectionPool = jdbcDatabaseConnectionPool;
	}

	@Value("${mysql.jdbc.database.connection.pool}")
	private String jdbcDatabaseConnectionPool;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
	    return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public DataSource setDataSource(){
		DriverManagerDataSource driverManagerDatasource=new DriverManagerDataSource();
		driverManagerDatasource.setDriverClassName(jdbcDriverName);
		driverManagerDatasource.setUsername(jdbcDatabaseUsername);
		driverManagerDatasource.setUrl(jdbcConnectionUrl);
		driverManagerDatasource.setPassword(jdbcDatabasePassword);
		return driverManagerDatasource;
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager(){
		return new DataSourceTransactionManager(setDataSource());
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(setDataSource());
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public ObjectMapper objectMapper()
	{
		ObjectMapper objectMapper=new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper;
	}
}
