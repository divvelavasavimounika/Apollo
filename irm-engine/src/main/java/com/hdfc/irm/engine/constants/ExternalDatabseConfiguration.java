package com.hdfc.irm.engine.constants;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class ExternalDatabseConfiguration {

	@Bean(name = "extDbProps")
	@ConfigurationProperties(prefix = "spring.datasource.ext")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "extDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.ext")
	public DataSource extDataSource(@Qualifier("extDbProps") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties defaultDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSource dataSource() {
		return defaultDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean(name = "extJdbcTemplate")
	public JdbcTemplate createJdbcTemplate(@Qualifier("extDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
