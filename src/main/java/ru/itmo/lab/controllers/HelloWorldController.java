package ru.itmo.lab.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Hello World Controller", description = "Тестовый контроллер")
public class HelloWorldController {
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
    
    @GetMapping("/user")
    public String getUser(Authentication authentication) {
        return "Authenticated as: " + authentication.getName();
    }
}
