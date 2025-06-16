package ru.itmo.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.lab.models.User;
import ru.itmo.lab.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<User> loginUser(String login, String password) {
        Optional<User> user = userRepository.findByUsername(login);
        boolean loginSucceeded = user
                .map(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElse(false);
        return user.filter(a -> loginSucceeded);
    }
}
