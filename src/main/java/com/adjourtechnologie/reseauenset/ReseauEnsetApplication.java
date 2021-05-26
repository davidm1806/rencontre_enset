package com.adjourtechnologie.reseauenset;

import com.pusher.rest.Pusher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ReseauEnsetApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReseauEnsetApplication.class, args);
	}


	@Bean
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}


}
