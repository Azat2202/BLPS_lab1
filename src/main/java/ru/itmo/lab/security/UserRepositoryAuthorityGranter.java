package ru.itmo.lab.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import ru.itmo.lab.repositories.UserRepository;

import java.security.Principal;
import java.util.Set;

@RequiredArgsConstructor
public class UserRepositoryAuthorityGranter implements AuthorityGranter {

    private final UserRepository userRepository;

    @Override
    public Set<String> grant(Principal principal) {
//        final var role = userRepository.findRoleByEmail(principal.getName());
        return Set.of("ROLE_USER");
//        return role.map(value -> Set.of(value.toString())).orElse(null);
    }
}
