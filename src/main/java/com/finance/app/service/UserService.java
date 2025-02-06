package com.finance.app.service;

import com.finance.app.model.User;
import com.finance.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service class responsible for managing user-related operations such as
 * registration.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Registers a new user if the username and email are not already in use.
     *
     * @param user the User object containing registration details such as username,
     *             password, email, first name, last name, and role.
     * @throws IllegalArgumentException if the username or email is already in use.
     */
    public void registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username is already in use");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        User userData = new User();
        userData.setUsername(user.getUsername());
        userData.setPassword(passwordEncoder.encode(user.getPassword()));
        userData.setEmail(user.getEmail());
        userData.setFirstName(user.getFirstName());
        userData.setLastName(user.getLastName());
        userData.setRole(user.getRole());
        userData.setCreatedDate(LocalDateTime.now().toString());
        userData.setUpdatedDate(LocalDateTime.now().toString());

        userRepository.save(userData);
    }

}
