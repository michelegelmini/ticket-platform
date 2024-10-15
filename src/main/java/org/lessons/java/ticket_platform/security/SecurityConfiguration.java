package org.lessons.java.ticket_platform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@SuppressWarnings("removal")
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/tickets/create").hasAuthority("ADMIN")
                        .requestMatchers("/tickets/edit/*").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/tickets/**", "/tickets/edit/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/users/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/categories/**").hasAuthority("ADMIN")
                        .requestMatchers("/users").hasAnyAuthority("ADMIN")
                        .requestMatchers("/categories").hasAuthority("ADMIN")
                        .requestMatchers("/tickets", "/tickets/*").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers("/pages").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
        )
        .exceptionHandling(handling -> handling
                .accessDeniedPage("/pages/error"))
                .formLogin(withDefaults())
                .logout(withDefaults())
                .csrf(csrf -> csrf.disable());
		return http.build();
	}

	@Bean
	DatabaseUserDetailsService usersDetailsService() {
		return new DatabaseUserDetailsService();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(usersDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;

	}

}
