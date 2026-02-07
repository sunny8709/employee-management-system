package com.employee.service;

import com.employee.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

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
            System.out.println("Login successful for user: " + user.getUsername());
            return true;
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            return false;
        }
    }
}
