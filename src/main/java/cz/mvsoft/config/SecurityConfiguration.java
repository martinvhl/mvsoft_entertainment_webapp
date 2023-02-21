package cz.mvsoft.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.NoArgsConstructor;

@Configuration
@EnableWebSecurity
@NoArgsConstructor
public class SecurityConfiguration {

	@Autowired
	@Qualifier("securityDataSource")
	DataSource securityDataSource;
	
	public SecurityConfiguration(DataSource securityDataSource) {
		this.securityDataSource = securityDataSource;
	}
	
	UserDetailsManager userDetailsManager() {
		return new JdbcUserDetailsManager(securityDataSource);
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests(authorization -> //todo roles cz.springboot.config.SecurityConfig in SpringBootThymeleafHelloWorld
				authorization
					.antMatchers("/**").permitAll())
			.formLogin(authorization ->
					authorization
						.loginPage("/login/showLoginPage")
						.loginProcessingUrl("/authenticateTheUser")
						.permitAll())
			.logout(LogoutConfigurer::permitAll)
			.exceptionHandling(authorization ->
					authorization
						.accessDeniedPage("/access-denied")
					);
		return httpSecurity.build();
	}
}
