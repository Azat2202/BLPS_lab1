package ru.itmo.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.lab.models.Booking;
import ru.itmo.lab.models.User;
import ru.itmo.lab.models.enums.Role;
import ru.itmo.lab.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Override
	@Transactional
	public void run(String... args) {
		if (userRepository.count() == 6) {
			createUser("u", "u", Role.USER);
			createUser("ho", "ho", Role.HOTEL_OWNER);
			createUser("ha", "ha", Role.HOTEL_ADMIN);
			createUser("sa", "sa", Role.SYSTEM_ADMIN);
			createUser("ta", "ta", Role.TRANSACTION_APPROVER);
		}
	}
	
	private void createUser(String username, String password, Role role) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(role);
		userRepository.save(user);
	}
}
