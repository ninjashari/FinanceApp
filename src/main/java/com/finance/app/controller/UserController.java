package com.finance.app.controller;

import com.finance.app.model.JwtResponse;
import com.finance.app.model.LoginRequest;
import com.finance.app.model.Status;
import com.finance.app.model.User;
import com.finance.app.repository.RoleRepository;
import com.finance.app.repository.UserRepository;
import com.finance.app.security.jwt.JwtUtils;
import com.finance.app.security.model.UserDetailsImpl;
import com.finance.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for managing user-related endpoints.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

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
            status.setCode(HttpStatus.BAD_REQUEST.value());
            status.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            status.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status);
        }
    }

    /**
     * Authenticates a user and generates a JWT token upon successful authentication.
     *
     * @param loginRequest the LoginRequest object containing the user's login credentials (username and password).
     * @return a ResponseEntity containing a JwtResponse object with the JWT token, user ID, username, email, and roles if authentication is successful.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user using the provided username and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Set the authenticated user in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate a JWT token for the authenticated user
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Retrieve the authenticated user's details
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Extract the roles of the authenticated user
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            // Return the JWT token and user details in the response
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));
        } catch (Exception exception) {
            Status status = new Status();
            status.setCode(HttpStatus.UNAUTHORIZED.value());
            status.setStatus(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            status.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(status);
        }

    }

}
