package com.ntd.project.security.controller;

import com.ntd.project.security.dto.AuthDto;
import com.ntd.project.security.dto.LoginResponseDto;
import com.ntd.project.security.dto.RegisterDto;
import com.ntd.project.security.repository.UserRepository;
import com.ntd.project.security.service.AuthorizationService;
import com.ntd.project.security.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizationService authorizationService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ApplicationContext applicationContext;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testLogin() throws Exception {
        // Arrange
        AuthDto authDto = new AuthDto("username", "password");
        LoginResponseDto responseDto = new LoginResponseDto("token");
        when(authorizationService.login(any(AuthDto.class)))
                .thenReturn(ResponseEntity.ok(responseDto));

        String jsonRequest = """
                    {
                        "username": "username",
                        "password": "password"
                    }
                """;

        String jsonResponse = """
                    {
                        "token": "token"
                    }
                """;

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void testRegister() throws Exception {
        // Arrange
        RegisterDto registerDto = new RegisterDto("username", "password", "admin", 20L);
        when(authorizationService.register(registerDto)).thenReturn(ResponseEntity.ok().build());

        String jsonRequest = """
                    {
                        "username": "username",
                        "password": "password",
                        "role": "admin",
                        "balance": 20
                    }
                """;

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }
}
