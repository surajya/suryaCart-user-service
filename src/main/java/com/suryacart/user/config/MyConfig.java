package com.suryacart.user.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.suryacart.user.model.entity.User;
import com.suryacart.user.repository.UserRepositoryImpl;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@EnableWebSecurity
public class MyConfig {

	@Bean
	public UserDetailsService userDetailsService(UserRepositoryImpl repo) {
		return username -> {
			User user = repo.getUserByUserName(username);

			if (user == null) {
				throw new UsernameNotFoundException("User not found");
			}

			return org.springframework.security.core.userdetails.User
					.withUsername(user.getEmail())
					.password(user.getPassword())
					.roles(user.getRole())
					.build();
		};
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

	@Bean
	public OpenAPI productServiceOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("SuryaCart Product Service API")
						.description("Product management APIs for SuryaCart")
						.version("v1.0"));
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
