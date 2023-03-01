package cz.mvsoft.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
//@EnableJpaRepositories("cz.mvsoft.dao")
public class DataSourcesConfiguration {
	
	@Primary
	@Bean(name = "entertainmentDataSource")
	@ConfigurationProperties(prefix = "entertainment.datasource")
	DataSource entertainmentDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "securityDataSource")
	@ConfigurationProperties(prefix = "security.datasource")
	DataSource securityDataSource() {
		return DataSourceBuilder.create().build();
	}
	
//	@Bean
//	@ConfigurationProperties(prefix = "spring.data.jpa.entity")
//	LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("securityDataSource") DataSource appDataSource) {
//		return builder.dataSource(appDataSource).build();
//	}
}
