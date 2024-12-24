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
import org.springframework.web.bind.annotation.*;

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
            status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            status.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            status.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

}
