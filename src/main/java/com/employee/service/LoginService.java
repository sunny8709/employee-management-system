package com.employee.service;

import com.employee.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User Login Module
 * Responsibilities:
 * - Accept user login details
 * - Pass credentials to authentication layer
 * - Redirect to dashboard upon successful login
 * 
 * Functional Flow: User → LoginService → AuthService → Dashboard
 */
@Service
@RequiredArgsConstructor
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private final AuthService authService;

    /**
     * Processes user login
     * 
     * @param username User's username
     * @param password User's password
     * @return User object if login successful
     * @throws RuntimeException if login fails
     */
    public User processLogin(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Username cannot be empty");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }

        // Delegate to AuthService for credential validation
        return authService.authenticateUser(username, password);
    }

    /**
     * Simple login method that returns boolean
     * 
     * @param username User's username
     * @param password User's password
     * @return true if login successful, false otherwise
     */
    public boolean login(String username, String password) {
        try {
            User user = processLogin(username, password);
            logger.info("Login successful for user: {}", user.getUsername());
            return true;
        } catch (Exception e) {
            String errorMessage = authService.handleAuthenticationException(e);
            logger.error("Login failed: {}", errorMessage);
            return false;
        }
    }
}
