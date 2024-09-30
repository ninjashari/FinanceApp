package com.finance.app.financeapp.controller;

import com.finance.app.financeapp.dto.User;
import com.finance.app.financeapp.security.JwtTokenUtil;
import com.finance.app.financeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

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

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserDetails(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }
}
