package com.finance.app.financeapp.service.impl;


import com.finance.app.financeapp.dto.User;
import com.finance.app.financeapp.repository.UserRepository;
import com.finance.app.financeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the UserService interface. Provides methods for user registration
 * and user retrieval by username.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * A repository for performing CRUD operations on User entities.
     * This repository extends JpaRepository to inherit standard
     * database operations and includes a method for finding a User
     * by their username.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * The PasswordEncoder used for encoding user passwords before storing them
     * in the database. This bean is typically autoconfigured by Spring Security
     * and is used to ensure that passwords are stored securely.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user by encoding their password and saving them to the repository.
     *
     * @param user the user to be registered
     * @return the registered user with an encoded password
     */
    @Override
    public User registerUser(User user) {
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Retrieves a User by their username.
     *
     * @param username the username of the user to be retrieved.
     * @return the User associated with the specified username.
     * @throws RuntimeException if no user is found with the given username.
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}