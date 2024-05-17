package com.nniett.kikaishin.app.service.dto.write;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {
    @NotNull(message = "Username must be provided.")
    @NotBlank(message = "Username must be provided.")
    private String username;
    @NotNull(message = "Password must be provided.")
    @NotBlank(message = "Password must be provided.")
    private String password;
}
