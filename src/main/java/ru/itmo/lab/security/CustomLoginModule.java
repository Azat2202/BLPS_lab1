package ru.itmo.lab.security;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class CustomLoginModule implements LoginModule {
	private Subject subject;
	private CallbackHandler callbackHandler;
	private CustomPrincipal customPrincipal;
	
	private static final String[][] USERS = {
			{"user1", "password1"},
			{"user2", "password2"}
	};
	
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		System.out.println(Arrays.toString(USERS[0]));
		System.out.println(Arrays.toString(USERS[1]));
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
			callbackHandler.handle(callbacks);
			String username = ((NameCallback) callbacks[0]).getName();
			String password = new String(((PasswordCallback) callbacks[1]).getPassword());
			
			for (String[] user : USERS) {
				if (user[0].equals(username) && user[1].equals(password)) {
					customPrincipal = new CustomPrincipal(username);
					return true;
				}
			}
			
			throw new FailedLoginException("Invalid credentials");
		} catch (IOException | UnsupportedCallbackException e) {
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