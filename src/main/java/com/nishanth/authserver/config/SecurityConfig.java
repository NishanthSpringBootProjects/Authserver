package com.nishanth.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nishanth.authserver.filter.JwtFilter;
import com.nishanth.authserver.service.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	/* using this bean, we can create our own security filter */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return 
		http.csrf(customizer -> customizer.disable() )     //Makes csrf disable
		.authorizeHttpRequests(request -> request
				.requestMatchers("user/register","user/login").permitAll()
				.anyRequest().authenticated())   //Makes all resquests authenticated
		.httpBasic(Customizer.withDefaults())         //it Makes postman/insomania to send requests
		.sessionManagement(session->
		                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   //Makes the session stateless
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		
		.build();
	}
	
	/*
	 * AuthenticationProvider is used to set our own username and password and also
	 * able to Bcrypt and decrypt the password.using this bean,we can create our own user register api
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());  //for no password encoder
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	/*
	 * AuthenticationManager is used to validate our username and password and then
	 * calls are go through AuthenticationProvider.using this bean,we can create our own login api
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
	
	

}
