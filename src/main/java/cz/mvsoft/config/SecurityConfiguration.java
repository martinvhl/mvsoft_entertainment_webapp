package cz.mvsoft.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import cz.mvsoft.service.UserService;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity nám umožňuje využívat anotací @PreAuthorized u endpointů, nahrazuje deprecated @EnableGlobalMethodSecurity
public class SecurityConfiguration {

	@Autowired
    private UserService userService;
	
	@Autowired
	@Qualifier("securityDataSource")
	DataSource securityDataSource;
	
	@Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	public SecurityConfiguration(DataSource securityDataSource) {
		this.securityDataSource = securityDataSource;
	}
	
	@Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service / or UserDetailsManager - prepared service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(authorization ->
				authorization
					.antMatchers("/**").permitAll())
			.formLogin(authorization ->
					authorization
						.loginPage("/login/showLoginPage")
						.loginProcessingUrl("/authenticateTheUser")
						.successHandler(customAuthenticationSuccessHandler)
						.permitAll())
			.logout(LogoutConfigurer::permitAll)
			.exceptionHandling(authorization ->
					authorization
						.accessDeniedPage("/access-denied")
					)
			.authenticationProvider(authenticationProvider());
	
		return httpSecurity.build();
	}
}
