package ru.itmo.lab.workers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.itmo.lab.dto.requests.BookingRequestDTO;
import ru.itmo.lab.models.User;
import ru.itmo.lab.repositories.UserRepository;
import ru.itmo.lab.services.BookingService;
import ru.itmo.lab.services.UserService;

import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginWorker {
    private final ExternalTaskClient client;
    private final UserService userService;

    @PostConstruct
    public void subscribe() {
        client.subscribe("login-user")
                .lockDuration(1000)
                .handler(this::execute)
                .open();
    }

    private void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String login = externalTask.getVariable("login");
        String password = externalTask.getVariable("password");

        log.info("Loggining user with login = {} and password = {}", login, password);

        Optional<User> user = userService.loginUser(login, password);

        log.info("Got user = {}", user.map(User::getUsername));

        externalTaskService.complete(externalTask,
                Variables.createVariables()
                        .putValue(
                                "is_logged_in",
                                user.isPresent()
                        )
        );

    }
}
