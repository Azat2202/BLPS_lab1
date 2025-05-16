package ru.itmo.lab;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableTransactionManagement
@OpenAPIDefinition
@EnableMethodSecurity
public class BlpsLab1Application {
	public static void main(String[] args) {
		SpringApplication.run(BlpsLab1Application.class, args);
	}
}
