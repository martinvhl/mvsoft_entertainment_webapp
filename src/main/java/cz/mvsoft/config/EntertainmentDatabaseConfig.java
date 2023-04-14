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
		basePackages = "cz.mvsoft.dao.entertainmentDao",
		entityManagerFactoryRef = "entertainmentEntityManagerFactory",
		transactionManagerRef = "entertainmentTransactionManager")
public class EntertainmentDatabaseConfig {
	
	@Autowired
	@Qualifier("entertainmentDataSource")
	private DataSource dataSource;

	@Bean
	LocalContainerEntityManagerFactoryBean entertainmentEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource).packages("cz.mvsoft.entity.entertainment").build();
	}
	
	@Bean
	PlatformTransactionManager entertainmentTransactionManager(@Qualifier("entertainmentEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}