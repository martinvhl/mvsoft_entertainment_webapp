package cz.mvsoft.config;

import org.springframework.beans.factory.annotation.Autowired;
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
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

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
	
    //TODO pro zabezpečení MVC aplikace je csrf doporučován, pro REST naopak -> změnit csrf na JWT zabezpečení!!!
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(authorization ->
			authorization
					.antMatchers("/","/register/**").permitAll()
					.antMatchers("/films/list","/games/list").hasRole("BASIC")
					.antMatchers("/films/showAddFilmForm","/films/addFilm","/games/showGameFilmForm","/games/addGame").hasRole("ADMIN")
					.anyRequest().permitAll()
					)
			.exceptionHandling(authorization ->
					authorization
						.accessDeniedPage("/login/access-denied")
					)
			.formLogin(authorization ->
					authorization
						.loginPage("/login/showLoginPage")
						.loginProcessingUrl("/authenticateTheUser")
						.successHandler(customAuthenticationSuccessHandler)
						.permitAll())
			.logout(LogoutConfigurer::permitAll)
			.authenticationProvider(authenticationProvider())
			.httpBasic(); //pak v Postmanu přidat user credentials do authorization (nezapomenout přepnout do Basic auth)
	
		return httpSecurity.build();
	}
}
