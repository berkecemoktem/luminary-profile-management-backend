package com.luminary.profilemanagement.rest;

import com.luminary.profilemanagement.model.AuthUser;
import com.luminary.profilemanagement.model.dto.AuthDto;
import com.luminary.profilemanagement.service.AuthService;
import com.luminary.profilemanagement.service.JpaUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final JpaUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, JpaUserDetailsService userDetailsService,
                          AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto.LoginRequest userLogin) {
        log.debug("Login attempt for user: {}", userLogin.username());

        AuthUser userDetails;
        try {
            userDetails = (AuthUser) userDetailsService.loadUserByUsername(userLogin.username());
        } catch (UsernameNotFoundException e) {
            log.error("User not found: {}", userLogin.username());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Directly compare the plaintext password for testing
        if (!userLogin.password().equals(userDetails.getPassword())) {
            log.error("Bad credentials for user: {}", userLogin.username());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = authService.generateToken(authentication);
        return ResponseEntity.ok(new AuthDto.Response("User logged in successfully", token));
    }

}




    /*@PostMapping("/token")
    public ResponseEntity<String> token(Authentication authentication) {
        LOG.debug("Token requested for user: '{}'", authentication.getName());
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required");
        }
        String token = authService.generateToken(authentication);
        LOG.debug("Token granted: {}", token);
        return ResponseEntity.ok(token);
    }*/

