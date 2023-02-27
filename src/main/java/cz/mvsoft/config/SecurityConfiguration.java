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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity nám umožňuje využívat anotací @PreAuthorized u endpointů, nahrazuje deprecated @EnableGlobalMethodSecurity
public class SecurityConfiguration {

//	@Autowired
//    private UserService userService;
	
	@Autowired
	@Qualifier("securityDataSource")
	DataSource securityDataSource;
	
	public SecurityConfiguration() {}

	public SecurityConfiguration(DataSource securityDataSource) {
		this.securityDataSource = securityDataSource;
	}
	
	@Bean
	UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
		return new JdbcUserDetailsManager(securityDataSource);
	}
	
	@Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsManager(passwordEncoder())); //set the custom user details service / or UserDetailsManager - prepared service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

//	@Bean
//	UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//		return new UserServiceImpl();
//	}
	
//	@Bean //nejprve ukázka authentication hard-coded uživatelů
//	UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//		//Hard coded users
//		UserDetails admin = User.withUsername("Martin").password(passwordEncoder.encode("banikpico")).roles("ADMIN").build();
//		UserDetails user = User.withUsername("John").password(passwordEncoder.encode("fucksparta")).roles("USER").build();
//		return new InMemoryUserDetailsManager(admin,user);
//	}
//	
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//		return httpSecurity.csrf().disable()
//				.authorizeHttpRequests()
//					.antMatchers("/api/v1/films").permitAll()
//					.and()
//				.authorizeHttpRequests()
//					.antMatchers("/api/v1/films/helloworld").authenticated()
//					.and()
//				.formLogin().and()
//				.build();
//	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(authorization ->
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
					)
			.userDetailsService(userDetailsManager(passwordEncoder()))
			.authenticationProvider(authenticationProvider());
		return httpSecurity.build();
	}
}
