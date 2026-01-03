package com.suryacart.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MyConfig {

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		//CSRF protection is disabled, /admin/** is accessible only by ADMIN users, and /userControll/** is accessible only by USER users.
		//All other endpoints are public and do not require authentication.
		http.csrf(CsrfConfigurer::disable)
				.authorizeHttpRequests(requests -> requests
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/userControll/**").hasRole("USER")
						.requestMatchers("/**").permitAll());

		//A custom login page is used at /signin, and login requests are processed at /dologin.
		//After successful login, the user is redirected to /userControll/index.
		http.formLogin(login -> login
				.loginPage("/signin").loginProcessingUrl("/dologin").defaultSuccessUrl("/userControll/index"));

		return http.build();
	}

}
