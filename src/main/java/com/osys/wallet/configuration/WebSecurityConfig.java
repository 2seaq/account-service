// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.osys.wallet.service.CustomOidcUserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private CustomOidcUserService customOidcUserService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/built/**", "/main.css").permitAll()
				.anyRequest().authenticated()
			)		
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(userInfo -> userInfo
					.oidcUserService(customOidcUserService)
				)
			)
			.csrf(csrf -> csrf.disable())
			.headers(frameOptions -> frameOptions.disable())
			.httpBasic(Customizer.withDefaults())
			.logout((logout) -> logout.permitAll()				
			);

		return http.build();
	}
	
	/*
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
	return new SecurityEvaluationContextExtension();
	}
	*/
}