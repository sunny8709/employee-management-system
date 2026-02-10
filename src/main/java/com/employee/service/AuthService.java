package com.employee.service;

import com.employee.exception.InvalidInputException;
import com.employee.exception.ResourceNotFoundException;
import com.employee.model.User;
import com.employee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;

    public boolean validateCredentials(String username, String password) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElse(null);

            if (user == null) {
                logger.error("User not found: {}", username);
                return false;
            }

            // Simple password validation (plain text comparison)
            boolean isValid = user.getPassword().equals(password);

            if (isValid) {
                logger.info("Credentials validated successfully for user: {}", username);
            } else {
                logger.error("Invalid password for user: {}", username);
            }

            return isValid;
        } catch (Exception e) {
            logger.error("Error validating credentials: {}", e.getMessage());
            return false;
        }
    }

    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        if (!user.getPassword().equals(password)) {
            throw new InvalidInputException("Invalid credentials");
        }

        logger.info("User authenticated successfully: {}", username);
        return user;
    }

    public boolean verifyUserAuthorization(String username, String requiredRole) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElse(null);

            if (user == null) {
                logger.error("User not found for authorization check: {}", username);
                return false;
            }

            boolean isAuthorized = user.getRole().equals(requiredRole);

            if (isAuthorized) {
                logger.info("User {} authorized for role: {}", username, requiredRole);
            } else {
                logger.warn("User {} not authorized for role: {}", username, requiredRole);
            }

            return isAuthorized;
        } catch (Exception e) {
            logger.error("Error verifying user authorization: {}", e.getMessage());
            return false;
        }
    }

    public String handleAuthenticationException(Exception e) {
        logger.error("Authentication exception occurred: {}", e.getMessage());

        if (e.getMessage().contains("User not found")) {
            return "Invalid username. Please try again.";
        } else if (e.getMessage().contains("Invalid credentials")) {
            return "Invalid password. Please try again.";
        } else {
            return "Authentication failed. Please contact administrator.";
        }
    }
}
