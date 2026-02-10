package com.employee.service;

import com.employee.model.User;
import com.employee.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthService
 * Tests credential validation, authentication, and authorization
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Tests")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setRole("ADMIN");
    }

    // ==================== TC-001: Credential Validation ====================

    @Test
    @DisplayName("TC-001.1: Valid credentials should return true")
    void testValidateCredentials_ValidCredentials_ReturnsTrue() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // Act
        boolean result = authService.validateCredentials("testuser", "password123");

        // Assert
        assertTrue(result, "Valid credentials should return true");
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    @DisplayName("TC-001.2: Invalid password should return false")
    void testValidateCredentials_InvalidPassword_ReturnsFalse() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // Act
        boolean result = authService.validateCredentials("testuser", "wrongpassword");

        // Assert
        assertFalse(result, "Invalid password should return false");
    }

    @Test
    @DisplayName("TC-001.3: Non-existent user should return false")
    void testValidateCredentials_UserNotFound_ReturnsFalse() {
        // Arrange
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act
        boolean result = authService.validateCredentials("nonexistent", "password123");

        // Assert
        assertFalse(result, "Non-existent user should return false");
    }

    // ==================== TC-002: User Authentication ====================

    @Test
    @DisplayName("TC-002.1: Successful authentication returns User object")
    void testAuthenticateUser_ValidCredentials_ReturnsUser() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // Act
        User result = authService.authenticateUser("testuser", "password123");

        // Assert
        assertNotNull(result, "Authenticated user should not be null");
        assertEquals("testuser", result.getUsername());
        assertEquals("ADMIN", result.getRole());
    }

    @Test
    @DisplayName("TC-002.2: Authentication with invalid credentials throws exception")
    void testAuthenticateUser_InvalidCredentials_ThrowsException() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.authenticateUser("testuser", "wrongpassword");
        });

        assertTrue(exception.getMessage().contains("Invalid credentials"));
    }

    @Test
    @DisplayName("TC-002.3: Authentication with non-existent user throws exception")
    void testAuthenticateUser_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.authenticateUser("nonexistent", "password123");
        });

        assertTrue(exception.getMessage().contains("User not found"));
    }

    // ==================== TC-003: Authorization Verification ====================

    @Test
    @DisplayName("TC-003.1: User with correct role should be authorized")
    void testVerifyUserAuthorization_CorrectRole_ReturnsTrue() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // Act
        boolean result = authService.verifyUserAuthorization("testuser", "ADMIN");

        // Assert
        assertTrue(result, "User with ADMIN role should be authorized for ADMIN");
    }

    @Test
    @DisplayName("TC-003.2: User with incorrect role should not be authorized")
    void testVerifyUserAuthorization_IncorrectRole_ReturnsFalse() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // Act
        boolean result = authService.verifyUserAuthorization("testuser", "USER");

        // Assert
        assertFalse(result, "User with ADMIN role should not be authorized for USER");
    }

    @Test
    @DisplayName("TC-003.3: Non-existent user should not be authorized")
    void testVerifyUserAuthorization_UserNotFound_ReturnsFalse() {
        // Arrange
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act
        boolean result = authService.verifyUserAuthorization("nonexistent", "ADMIN");

        // Assert
        assertFalse(result, "Non-existent user should not be authorized");
    }

    // ==================== TC-004: Exception Handling ====================

    @Test
    @DisplayName("TC-004.1: Handle user not found exception")
    void testHandleAuthenticationException_UserNotFound() {
        // Arrange
        Exception exception = new RuntimeException("User not found: testuser");

        // Act
        String message = authService.handleAuthenticationException(exception);

        // Assert
        assertEquals("Invalid username. Please try again.", message);
    }

    @Test
    @DisplayName("TC-004.2: Handle invalid credentials exception")
    void testHandleAuthenticationException_InvalidCredentials() {
        // Arrange
        Exception exception = new RuntimeException("Invalid credentials");

        // Act
        String message = authService.handleAuthenticationException(exception);

        // Assert
        assertEquals("Invalid password. Please try again.", message);
    }

    @Test
    @DisplayName("TC-004.3: Handle generic exception")
    void testHandleAuthenticationException_GenericError() {
        // Arrange
        Exception exception = new RuntimeException("Database connection error");

        // Act
        String message = authService.handleAuthenticationException(exception);

        // Assert
        assertEquals("Authentication failed. Please contact administrator.", message);
    }

    // ==================== TC-005: Edge Cases ====================

    @Test
    @DisplayName("TC-005.1: Null username should return false")
    void testValidateCredentials_NullUsername_ReturnsFalse() {
        // Act
        boolean result = authService.validateCredentials(null, "password123");

        // Assert
        assertFalse(result, "Null username should return false");
    }

    @Test
    @DisplayName("TC-005.2: Null password should return false")
    void testValidateCredentials_NullPassword_ReturnsFalse() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // Act
        boolean result = authService.validateCredentials("testuser", null);

        // Assert
        assertFalse(result, "Null password should return false");
    }

    @Test
    @DisplayName("TC-005.3: Empty username should return false")
    void testValidateCredentials_EmptyUsername_ReturnsFalse() {
        // Act
        boolean result = authService.validateCredentials("", "password123");

        // Assert
        assertFalse(result, "Empty username should return false");
    }
}
