package ru.itmo.lab;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableTransactionManagement
@OpenAPIDefinition
@EnableMethodSecurity
public class SearcherApplication {
	public static void main(String[] args) {
		SpringApplication.run(SearcherApplication.class, args);
	}
}
