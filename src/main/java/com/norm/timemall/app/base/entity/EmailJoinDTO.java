package com.norm.timemall.app.base.entity;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class EmailJoinDTO {
    @Email(message = "email format incorrect")
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = " qrcode is required")
    private String qrcode;
}
