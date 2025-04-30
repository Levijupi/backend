package com.barbearia.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        
        System.out.println("URL: " + System.getenv("DB_URL"));
		System.out.println("USER: " + System.getenv("DB_USER"));
		System.out.println("PASS: " + System.getenv("DB_PASS"));
    }
}