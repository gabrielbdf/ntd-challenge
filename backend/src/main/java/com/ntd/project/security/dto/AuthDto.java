package com.ntd.project.security.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthDto(
        @Size(min = 3, max = 20) @NotNull String username,
        @Size(min = 4, max = 20) @NotNull String password) {
}
