package com.luminary.profilemanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/**").permitAll() // Adjust your endpoints as needed
                                .anyRequest().authenticated() // Ensure other requests are authenticated
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF for simplicity; adjust as needed for production

        return http.build();
    }
}
