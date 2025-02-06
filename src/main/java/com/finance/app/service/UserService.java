package com.finance.app.service;

import com.finance.app.model.User;
import com.finance.app.repository.UserRepository;
import com.finance.app.security.jwt.JwtUtils;
import com.finance.app.util.CommonUtil;
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

    @Autowired
    JwtUtils jwtUtils;

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
        userData.setCreatedDate(LocalDateTime.now());
        userData.setUpdatedDate(LocalDateTime.now());

        userRepository.save(userData);
    }

    /**
     * Retrieves the User object associated with the provided token.
     *
     * @param token the token used for user retrieval
     * @return the User object corresponding to the token
     * @throws Exception if the token is invalid or the user is not found
     */
    public User getUser(String token) throws Exception {
        if (token != null && !token.isEmpty()) {
            User user = getUserFromToken(CommonUtil.extractToken(token));
            if (user != null) {
                return user;
            } else {
                throw new Exception("User not found");
            }
        } else {
            throw new Exception("Token is invalid");
        }
    }

    /**
     * Updates the user information based on the provided token and user data.
     *
     * @param token the authentication token for user validation.
     * @param user the User object containing updated user information like first name, last name, and email.
     * @return the updated User object with new information.
     * @throws Exception if the user associated with the token is not found.
     */
    public User updateUser(String token, User user) throws Exception {
        User existingUser = getUser(token);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setUpdatedDate(LocalDateTime.now());
            return userRepository.save(existingUser);
        } else {
            throw new Exception("User not found");
        }

    }

    /**
     * Retrieves a User object based on the provided JWT token.
     *
     * @param jwtToken the JWT token from which the user information will be extracted
     * @return a User object corresponding to the given JWT token
     * @throws Exception if the token validation fails or the user is not found
     */
    public User getUserFromToken(String jwtToken) throws Exception {
        // Validate the token using JWT utility
        if (jwtUtils.validateJwtToken(jwtToken)) {
            // Extract the username from the token
            String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
            // Get the user details from the repository
            return userRepository.findByUsername(username).orElseThrow(() -> new Exception("User Not Found with username: " + username));
        }
        return null;
    }

}
