package com.seproj.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HumanAuthenticatorRESTApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(HumanAuthenticatorRESTApplication.class, args);
		
		System.out.println("started");
		
	}

}
