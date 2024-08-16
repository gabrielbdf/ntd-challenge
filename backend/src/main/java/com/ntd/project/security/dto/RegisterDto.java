package com.ntd.project.security.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull String username, @NotNull String password, @NotNull String role, Long balance) {

}
