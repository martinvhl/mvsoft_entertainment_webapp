package cz.mvsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import cz.mvsoft.config.DataSourcesConfiguration;
import cz.mvsoft.config.EntertainmentDatabaseConfig;
import cz.mvsoft.config.SecurityDatabaseConfig;

@SpringBootApplication
@Import({DataSourcesConfiguration.class, EntertainmentDatabaseConfig.class, SecurityDatabaseConfig.class})
public class FilmDatabaseByMvSoftApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmDatabaseByMvSoftApplication.class, args);
	}

}
