package com.mariworld.club;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClubSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubSecurityApplication.class, args);
	}

}
