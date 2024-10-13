package com.luminary.profilemanagement.rest;

import com.luminary.profilemanagement.model.LoginMessage;
import com.luminary.profilemanagement.model.User;
import com.luminary.profilemanagement.model.dto.LoginDto;
import com.luminary.profilemanagement.model.dto.RequestPasswordResetDto;
import com.luminary.profilemanagement.model.dto.UserDto;
import com.luminary.profilemanagement.service.LoginService;
import com.luminary.profilemanagement.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginMessage> login(@RequestBody LoginDto loginDto) {
        LoginMessage response = loginService.loginUser(loginDto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDto userDto) {
        String message = loginService.saveUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }


    // Retrieve User by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> {
                    UserDto dto = convertToDTO(user);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*@GetMapping()
    public ResponseEntity<UserDto> getUsers() {
        return userService.getUserById()
                .map(user -> {
                    UserDto dto = convertToDTO(user);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }*/

    // Retrieve User by Username
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(user -> {
                    UserDto dto = convertToDTO(user);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Retrieve User by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(user -> {
                    UserDto dto = convertToDTO(user);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update User Email
    @PutMapping("/{id}/email")
    public ResponseEntity<UserDto> updateUserEmail(@PathVariable Long id, @RequestParam String newEmail) {
        User updatedUser = userService.updateUserEmail(id, newEmail);
        UserDto dto = convertToDTO(updatedUser);
        return ResponseEntity.ok(dto);
    }

    // Update User Password
    @PutMapping("/{id}/password")
    public ResponseEntity<UserDto> updateUserPassword(@PathVariable Long id, @RequestParam String newPassword) {
        User updatedUser = userService.updateUserPassword(id, newPassword);
        UserDto dto = convertToDTO(updatedUser);
        return ResponseEntity.ok(dto);
    }

    /* Reset User Password
    @PostMapping("/{id}/reset-password")
    public ResponseEntity<UserDto> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        User updatedUser = userService.resetPassword(id, newPassword);
        UserDto dto = convertToDTO(updatedUser);
        return ResponseEntity.ok(dto);
    }*/

    // Endpoint to handle password reset request with token and new password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        log.info("received token = " + token);
        log.info("received new password = " + newPassword);
        String result = userService.resetPasswordWithToken(token, newPassword);
        if ("valid".equals(result)) {
            return ResponseEntity.ok("Password has been reset successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
        }
    }


    @PostMapping("/requestPasswordReset")
    public ResponseEntity<String> requestPasswordReset(@RequestBody @Valid RequestPasswordResetDto requestDto) {
        // Call the service to handle sending the password reset email
        userService.generateAndSendPasswordResetToken(requestDto.getEmail());
        return ResponseEntity.ok("Password reset email sent.");
    }

    // Convert User to UserDTO
    private UserDto convertToDTO(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        // Do not include password in DTO for security reasons
        return dto;
    }
    @GetMapping("/validateResetToken")
    public ResponseEntity<String> validateResetToken(@RequestParam String token) {
        String result = userService.validatePasswordResetToken(token);
        if (result.equals("valid")) {
            return ResponseEntity.ok("Valid token");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }
}
