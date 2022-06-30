package com.codelikealexito.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScientistApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScientistApplication.class, args);
	}

}
