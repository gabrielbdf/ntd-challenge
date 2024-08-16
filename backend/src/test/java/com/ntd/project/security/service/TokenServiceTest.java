package com.ntd.project.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ntd.project.security.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mockStatic;

@RunWith(MockitoJUnitRunner.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a UserModel with some default values
        userModel = new UserModel();
        userModel.setUsername("testUser");
    }

    @Test
    void testGenerateTokenSuccess() {
        // Act
        String token = tokenService.generateToken(userModel);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());

        String subject = JWT.require(Algorithm.HMAC256("segredinho"))
                .withIssuer("auth")
                .build()
                .verify(token)
                .getSubject();

        assertEquals("testUser", subject);
    }

    @Test
    void testGenerateTokenFailure() {
        try (MockedStatic<Algorithm> algorithmMock = mockStatic(Algorithm.class)) {
            algorithmMock.when(() -> Algorithm.HMAC256(anyString())).thenThrow(new JWTCreationException("Error", null));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> tokenService.generateToken(userModel));
        }
    }

    @Test
    void testValidateTokenSuccess() {
        // Arrange
        String token = tokenService.generateToken(userModel);

        // Act
        String username = tokenService.validateToken(token);

        // Assert
        assertEquals("testUser", username);
    }

    @Test
    void testValidateTokenFailure() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act
        String result = tokenService.validateToken(invalidToken);

        // Assert
        assertEquals("", result);
    }

}
