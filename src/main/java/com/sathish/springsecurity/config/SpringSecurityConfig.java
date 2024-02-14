package com.sathish.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sathish.springsecurity.security.JwtAuthenticationEntryPoint;
import com.sathish.springsecurity.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {
	
	// Inject UserDetailsService
	private UserDetailsService userDetailsService;
	
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	private JwtAuthenticationFilter authenticationFilter;
	
	
	public SpringSecurityConfig(UserDetailsService userDetailsService,
															JwtAuthenticationEntryPoint authenticationEntryPoint,
															JwtAuthenticationFilter authenticationFilter) {
		this.userDetailsService = userDetailsService;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.authenticationFilter=authenticationFilter;
	}
	
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//configure AuthenticationManager Bean 
	// Authentication Manager will automatically uses UserDetailsService and password Encoder to authenticate
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(  authorize ->
//				authorize.anyRequest().authenticated()
				authorize
					.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
					.requestMatchers("/api/auth/**").permitAll()
					.anyRequest().authenticated()
					
				).exceptionHandling(exception -> 
								exception.authenticationEntryPoint(authenticationEntryPoint)
				).sessionManagement(session ->
								session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);
		
		
		http.addFilterBefore(authenticationFilter,UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
}




//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails sathish = User.builder()
//				.username("sathish")
//				.password(passwordEncoder().encode("5482"))
//				.roles("ADMIN")
//				.build();
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//		UserDetails ram = User.builder()
//				.username("ram")
//				.password(passwordEncoder().encode("5482"))
//				.roles("USER")
//				.build();
//		return new InMemoryUserDetailsManager(sathish, admin, ram);
//	}



/*
 * 
 * 
 * @Configuration public class SpringSecurityConfig {
 * 
 * private UserDetailsService userDetailsService;
 * 
 * 
 * public SpringSecurityConfig() {
 * 
 * }
 * 
 * @Autowired public SpringSecurityConfig(UserDetailsService userDetailsService)
 * { super(); this.userDetailsService = userDetailsService; }
 * 
 * @Bean public static PasswordEncoder passwordEncoder(){ return new
 * BCryptPasswordEncoder(); }
 * 
 * @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws
 * Exception {
 * 
 * http.csrf().disable() .authorizeHttpRequests((authorize) -> {
 * authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
 * .requestMatchers("/api/auth/**").permitAll() .anyRequest().authenticated();
 * }).exceptionHandling(exception->exception.authenticationEntryPoint(
 * authenticationEntryPoint));
 * 
 * return http.build(); }
 * 
 * @Bean public AuthenticationManager
 * authenticationManager(AuthenticationConfiguration configuration) throws
 * Exception { return configuration.getAuthenticationManager(); } }
 */