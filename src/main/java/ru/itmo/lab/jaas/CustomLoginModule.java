package ru.itmo.lab.jaas;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.lab.models.User;
import ru.itmo.lab.repositories.UserRepository;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@RequiredArgsConstructor
public class CustomLoginModule implements LoginModule {
	private Subject subject;
	private CallbackHandler callbackHandler;
	private CustomPrincipal customPrincipal;
	private final UserRepository userRepository;
	
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
	}
	
	@Override
	public boolean login() throws LoginException {
		System.out.println("login");
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("Username: ");
		callbacks[1] = new PasswordCallback("Password: ", false);
		
		try {
			System.out.println(1);
			callbackHandler.handle(callbacks);
			String username = ((NameCallback) callbacks[0]).getName();
			String password = new String(((PasswordCallback) callbacks[1]).getPassword());
			System.out.println(username);
			System.out.println(password);
			User user = userRepository.findByUsername(username);
			System.out.println(user);
			System.out.println(user.getPassword());
			if (user.getPassword().equals(password)) return true;
			
			throw new FailedLoginException("Invalid credentials");
		} catch (IOException | UnsupportedCallbackException e) {
			System.out.println(e.getMessage());
			throw new LoginException("Error during authentication");
		}
	}
	
	@Override
	public boolean commit() {
		System.out.println("commit");
		if (customPrincipal != null && subject != null) {
			subject.getPrincipals().add(customPrincipal);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean abort() {
		System.out.println("abort");
		subject.getPrincipals().remove(customPrincipal);
		return false;
	}
	
	@Override
	public boolean logout() {
		System.out.println("logout");
		subject.getPrincipals().remove(customPrincipal);
		return true;
	}
}