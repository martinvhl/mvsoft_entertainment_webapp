package cz.mvsoft.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourcesConfiguration {
	
	@Bean(name = "entertainmentDataSource")
	@ConfigurationProperties(prefix = "entertainment.datasource")
	DataSource entertainmentDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name = "securityDataSource")
	@ConfigurationProperties(prefix = "security.datasource")
	DataSource securityDataSource() {
		return DataSourceBuilder.create().build();
	}
}
