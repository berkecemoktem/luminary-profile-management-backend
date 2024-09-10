package com.luminary.profilemanagement.rest;

import com.luminary.profilemanagement.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<String> token(Authentication authentication) {
        LOG.debug("Token requested for user: '{}'", authentication.getName());
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required");
        }
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted: {}", token);
        return ResponseEntity.ok(token);
    }
}
