package ru.itmo.lab;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class BlpsLab1Application {

    public static void main(String[] args) {
        SpringApplication.run(BlpsLab1Application.class, args);
    }

}
