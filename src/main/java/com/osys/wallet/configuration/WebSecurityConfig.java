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
import com.osys.wallet.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private CustomOidcUserService customOidcUserService;

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(requests -> requests
						.requestMatchers(
								"/login",
								"/built/**",
								"/main.css",
								"/oauth2/**",
								"/service-worker.js",
								"/manifest.json",
								"/service-worker.js",
								"/favicon.ico",
								"/logo192.png",
								"/logo512.png",
								"/apple-touch-icon.png",
								"/static/**")
						.permitAll()
						.anyRequest().authenticated())
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.permitAll())
				.oauth2Login(oauth2 -> oauth2
						.loginPage("/login")
						.defaultSuccessUrl("/", true) // <-- THIS IS IMPORTANT
						.userInfoEndpoint(userInfo -> userInfo
								.oidcUserService(customOidcUserService)
								.userService(customOAuth2UserService)))
				.csrf(csrf -> csrf.disable())
				.headers(frameOptions -> frameOptions.disable())
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

}