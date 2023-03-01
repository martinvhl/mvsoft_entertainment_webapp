package cz.mvsoft.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
		basePackages = "cz.mvsoft.dao.securityDao",
		entityManagerFactoryRef = "securityEntityManagerFactory",
		transactionManagerRef = "securityTransactionManager")
public class SecurityDatabaseConfig {

	@Autowired
	@Qualifier("securityDataSource")
	private DataSource dataSource;
	
	@Bean
	LocalContainerEntityManagerFactoryBean securityEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource).packages("cz.mvsoft.entity.users").build();
	}
	
	@Bean
	PlatformTransactionManager securityTransactionManager(@Qualifier("securityEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
