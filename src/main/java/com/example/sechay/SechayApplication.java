package com.example.sechay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.sechay")
public class SechayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SechayApplication.class, args);
	}

}
