package com.suryacart.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EntityScan(basePackages = "com.suryacart.user.model.entity")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
		log.info("Application started...");
	}

}
