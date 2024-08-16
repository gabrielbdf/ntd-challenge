package com.ntd.project.security.service;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ntd.project.security.dto.AuthDto;
import com.ntd.project.security.dto.LoginResponseDto;
import com.ntd.project.security.dto.RegisterDto;
import com.ntd.project.security.model.UserModel;
import com.ntd.project.security.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class AuthorizationService implements UserDetailsService {

    private final ApplicationContext applicationContext;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private AuthenticationManager authenticationManager;

    public AuthorizationService(ApplicationContext applicationContext, UserRepository userRepository,
                                TokenService tokenService) {
        this.applicationContext = applicationContext;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid AuthDto data) {
        authenticationManager = applicationContext.getBean(AuthenticationManager.class);
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    public ResponseEntity<Void> register(@RequestBody RegisterDto registerDto) {
        if (this.userRepository.findByUsername(registerDto.username()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
        UserModel newUser = new UserModel(registerDto.username(), encryptedPassword, registerDto.role(), registerDto.balance());
        newUser.setCreatedAt(new Date(System.currentTimeMillis()));
        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

}
