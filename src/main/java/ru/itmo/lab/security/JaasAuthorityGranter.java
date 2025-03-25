package ru.itmo.lab.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.web.client.HttpClientErrorException;
import ru.itmo.lab.models.User;
import ru.itmo.lab.repositories.UserRepository;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public class JaasAuthorityGranter implements AuthorityGranter {

    private final UserRepository userRepository;

    @Override
    public Set<String> grant(Principal principal) {
//        User user = userRepository
//                .findByUsername(principal.getName())
//                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "user not found"));
        return Collections.singleton("ROLE_USER");
    }
}