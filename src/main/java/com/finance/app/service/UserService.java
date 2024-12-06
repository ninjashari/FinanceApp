package com.finance.app.service;

import com.finance.app.config.JwtUtil;
import com.finance.app.dto.User;
import com.finance.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service class responsible for managing user-related operations such as
 * registration.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    /**
     * Authenticates a user by validating the provided username and password
     * against stored credentials and, if successful, generates a JWT token
     * for the user.
     *
     * @param username the username provided by the user attempting to authenticate
     * @param password the password provided by the user attempting to authenticate
     * @return a JWT token as a String if the authentication is successful;
     * returns null if the authentication fails
     */
    public String authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(username);
        }
        return null;
    }

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
