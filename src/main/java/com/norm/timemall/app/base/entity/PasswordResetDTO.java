package com.norm.timemall.app.base.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class PasswordResetDTO {
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "token is required")
    private String token;
}
