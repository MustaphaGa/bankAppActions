package com.example.bancApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BancAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancAppApplication.class, args);
	}

}
