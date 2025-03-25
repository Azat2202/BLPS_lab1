package ru.itmo.lab.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import ru.itmo.lab.jaas.CustomCallbackHandler;
import ru.itmo.lab.repositories.UserRepository;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserRepository userRepository;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, CustomAccessDeniedHandler customAccessDeniedHandler) throws Exception {
	    http.csrf(AbstractHttpConfigurer::disable)
			    .cors(withDefaults())
			    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			    .authorizeHttpRequests(auth -> auth
					    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
					    .requestMatchers("/api/public/**").permitAll()
					    .anyRequest().authenticated()
			    )
			    .authenticationProvider(jaasAuthenticationProvider())
			    .httpBasic(withDefaults())
			    .exceptionHandling(exception -> exception
					    .authenticationEntryPoint(authenticationEntryPoint)
					    .accessDeniedHandler(customAccessDeniedHandler)
			    );
	    return http.build();
    }
	
	@Bean
	public JaasAuthenticationProvider jaasAuthenticationProvider() {
		JaasAuthenticationProvider provider = new JaasAuthenticationProvider();
		provider.setLoginConfig(new ClassPathResource("jaas.config"));
		return provider;
	}
}
