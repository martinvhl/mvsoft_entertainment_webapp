package cz.mvsoft.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class SecurityDatabaseConfig {

	@Autowired
	@Qualifier("securityDataSource")
	private DataSource dataSource;
	
	@Bean
	@Primary
	LocalContainerEntityManagerFactoryBean securityEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource).packages("cz.mvsoft.entity.users").build();
	}
	
	@Bean
	@Primary
	PlatformTransactionManager securityTransactionManager(@Qualifier("securityEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
