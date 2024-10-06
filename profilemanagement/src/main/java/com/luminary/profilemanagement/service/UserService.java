package com.luminary.profilemanagement.service;

import com.luminary.profilemanagement.model.User;
import com.luminary.profilemanagement.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUserEmail(Long id, String newEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    public User updateUserPassword(Long id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        //user.setPassword(passwordEncoder.encode(newPassword));
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public User resetPassword(Long id, String newPassword) {
        return updateUserPassword(id, newPassword);
    }


    public void generateAndSendPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        user.setPasswordResetToken(token);
        user.setTokenExpirationDate(LocalDateTime.now().plusHours(1)); // Set expiration time
        userRepository.save(user);

        sendPasswordResetEmail(user.getEmail(), token);
    }

    public void sendPasswordResetEmail(String email, String token) {
        String resetLink = "http://localhost:8080/api/users/reset-password?token=" + token;  // Backend endpoint for testing, will be replaced with the UI endpoint!
        String subject = "Password Reset Request";
        String message = "To reset your password, click the link below:\n" + resetLink +
                "\n\nAlternatively, you can make a POST request to this URL with your new password.";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public String validatePasswordResetToken(String token) {
        User user = userRepository.findByPasswordResetToken(token)
                .orElse(null);

        if (user == null || user.getTokenExpirationDate().isBefore(LocalDateTime.now())) {
            return "invalid";
        }
        return "valid";
    }

    public String resetPasswordWithToken(String token, String newPassword) {
        // Validate the token and check expiration
        User user = userRepository.findByPasswordResetToken(token)
                .orElse(null);

        if (user == null || user.getTokenExpirationDate().isBefore(LocalDateTime.now())) {
            return "invalid";
        }

        // Reset password
        user.setPassword(newPassword);  // We may want to encode the password, later
        user.setPasswordResetToken(null);  // Clear the token after successful reset
        user.setTokenExpirationDate(null); // Clear token expiration
        userRepository.save(user);

        return "valid";
    }
}