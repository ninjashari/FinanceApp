package com.finance.app.financeapp.controller;

import com.finance.app.financeapp.dto.User;
import com.finance.app.financeapp.security.JwtTokenUtil;
import com.finance.app.financeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * A controller for handling HTTP requests related to user operations.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * Provides access to user-related services, including user registration and retrieval.
     */
    @Autowired
    private UserService userService;

    /**
     * Utility for handling JWT token operations such as generation, validation, and extracting claims.
     */
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Autowired instance of PasswordEncoder used to encode and decode passwords.
     *
     * This component is utilized in various functionalities such as
     * user registration and authentication to ensure password security.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user in the system.
     *
     * @param user the user information to register
     * @return a ResponseEntity containing the newly registered user
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    /**
     * Handles user login requests by validating credentials and generating JWT tokens.
     *
     * @param user The user details containing username and password for authentication.
     * @return A ResponseEntity containing the JWT token if authentication is successful,
     *         or an error message if authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        // Find the user by username
        User foundUser = userService.findByUsername(user.getUsername());

        if (foundUser != null && passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            // If passwords match, generate the JWT token
            String token = jwtTokenUtil.generateToken(foundUser.getUsername());
            return ResponseEntity.ok("Bearer " + token);
        }

        // Return error if credentials are invalid
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    /**
     * Retrieves the user details for the given username.
     *
     * @param username the username of the user whose details are to be retrieved
     * @return a ResponseEntity containing the user details if found
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserDetails(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }
}
