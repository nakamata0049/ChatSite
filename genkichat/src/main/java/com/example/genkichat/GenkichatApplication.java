package com.example.genkichat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport
@SpringBootApplication
public class GenkichatApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenkichatApplication.class, args);
	}

}
