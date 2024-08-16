package com.ntd.project.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ntd.project.security.dto.RegisterDto;
import com.ntd.project.security.service.AuthorizationService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class  InitRunner implements ApplicationRunner {

   private final AuthorizationService authorizationService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        authorizationService.register(
            new RegisterDto("admin", "admin", "ADMIN", 1000L)
        );
    }

}
