package com.finance.app.controller;

import com.finance.app.dto.Status;
import com.finance.app.dto.User;
import com.finance.app.dto.UserLogin;
import com.finance.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for managing user-related endpoints.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles the user signup process by registering a new user.
     *
     * @param user the User object containing the user's details for registration,
     *             including username, password, email, first name, and other
     *             necessary information.
     * @return a ResponseEntity containing the Status object indicating the
     * result of the signup process. Returns HTTP status 200 if the
     * registration is successful, or HTTP status 500 if an error
     * occurs during registration.
     */
    @PostMapping("/signup")
    public ResponseEntity<Status> signup(@Valid @RequestBody User user) {
        try {
            userService.registerUser(user);
            Status status = new Status();
            status.setCode(HttpStatus.OK.value());
            status.setStatus(HttpStatus.OK.getReasonPhrase());
            status.setMessage("User registered successfully");
            return ResponseEntity.status(HttpStatus.OK).body(status);
        } catch (Exception e) {
            Status status = new Status();
            status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            status.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            status.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
        }
    }

    /**
     * Handles the user login process by verifying user credentials.
     *
     * @param user the User object containing the user's login details,
     *             including username and password.
     * @return a ResponseEntity containing the Status object indicating the
     * result of the login process. Returns HTTP status 200 if the
     * login is successful, or HTTP status 401 if credentials are invalid.
     */
    @PostMapping("/login")
    public ResponseEntity<Status> login(@RequestBody UserLogin user) {
        try {
            String token = userService.authenticateUser(user.getUsername(), user.getPassword());
            Status status = new Status();
            if (token != null && !token.isBlank()) {
                status.setCode(HttpStatus.OK.value());
                status.setStatus(HttpStatus.OK.getReasonPhrase());
                status.setMessage("Login successful");
                status.setToken(token);
                return ResponseEntity.status(HttpStatus.OK).body(status);
            } else {
                status.setCode(HttpStatus.UNAUTHORIZED.value());
                status.setStatus(HttpStatus.UNAUTHORIZED.getReasonPhrase());
                status.setMessage("Invalid username or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(status);
            }
        } catch (Exception e) {
            Status status = new Status();
            status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            status.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            status.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
        }
    }

}
