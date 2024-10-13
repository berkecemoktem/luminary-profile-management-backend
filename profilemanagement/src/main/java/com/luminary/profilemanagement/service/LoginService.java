package com.luminary.profilemanagement.service;

import java.util.Optional;

import com.luminary.profilemanagement.model.LoginMessage;
import com.luminary.profilemanagement.model.User;
import com.luminary.profilemanagement.model.dto.LoginDto;
import com.luminary.profilemanagement.model.dto.UserDto;
import com.luminary.profilemanagement.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Save a new user in the database
    public String saveUser(UserDto userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);
        return "User registered successfully";
    }


    // Handle the login process
    public LoginMessage loginUser(LoginDto loginDto) {
        Optional<User> userOpt = userRepository.findByEmail(loginDto.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean isPasswordMatch = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

            if (isPasswordMatch) {
                return new LoginMessage("Login Success", true, user);
            } else {
                return new LoginMessage("Password does not match", false, null);
            }
        } else {
            return new LoginMessage("Email not found", false, null);
        }
    }
}
