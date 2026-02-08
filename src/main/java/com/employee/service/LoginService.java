package com.employee.service;

import com.employee.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private final AuthService authService;

    public User processLogin(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Username cannot be empty");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }

        return authService.authenticateUser(username, password);
    }

    public boolean login(String username, String password) {
        try {
            User user = processLogin(username, password);
            logger.info("Login successful for user: {}", user.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("Login failed: {}", e.getMessage());
            return false;
        }
    }
}
