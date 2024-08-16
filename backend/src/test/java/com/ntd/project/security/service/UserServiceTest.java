package com.ntd.project.security.service;

import com.ntd.project.security.model.UserModel;
import com.ntd.project.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserModel user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserModel();
        user.setUsername("testUser");
        user.setBalance(1000L);
    }

    @Test
    void testGetUserDetails() {
        // Arrange
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        // Act
        UserModel result = userService.getUserDetais("testUser");

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testDecreaseUserBalanceSuccess() {
        // Arrange
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        // Act
        UserModel result = userService.decreaseUserBalance(user, 500L);

        // Assert
        assertNotNull(result);
        assertEquals(500L, result.getBalance());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDecreaseUserBalanceFailure() {
        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            userService.decreaseUserBalance(user, 1500L);
        });

        verify(userRepository, never()).save(any(UserModel.class));
    }

    @Test
    void testUserCanOperateSuccess() {
        // Act
        boolean canOperate = userService.decreaseUserBalance(user, 500L) != null;

        // Assert
        assertTrue(canOperate);
        assertEquals(500L, user.getBalance());
    }

    @Test
    void testUserCanOperateFailure() {
        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            userService.decreaseUserBalance(user, 1500L);
        });

        assertEquals(1000L, user.getBalance()); // Balance should not change
    }
}
