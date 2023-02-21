package cz.mvsoft.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class DataSourcesConfiguration {
	
	@Bean
	@ConfigurationProperties(prefix = "app.datasource")
	DataSource entityDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "security.datasource")
	DataSource securityDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	//
	@Bean
	@ConfigurationProperties(prefix = "spring.data.jpa.entity")
	LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource appDataSource) {
		return builder.dataSource(appDataSource).build();
	}
}
