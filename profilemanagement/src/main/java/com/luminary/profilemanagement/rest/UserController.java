package com.luminary.profilemanagement.rest;

import com.luminary.profilemanagement.model.User;
import com.luminary.profilemanagement.model.dto.UserDto;
import com.luminary.profilemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

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

    // Reset User Password
    @PostMapping("/{id}/reset-password")
    public ResponseEntity<UserDto> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        User updatedUser = userService.resetPassword(id, newPassword);
        UserDto dto = convertToDTO(updatedUser);
        return ResponseEntity.ok(dto);
    }

    // Convert User to UserDTO
    private UserDto convertToDTO(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        // Do not include password in DTO for security reasons
        return dto;
    }
}
