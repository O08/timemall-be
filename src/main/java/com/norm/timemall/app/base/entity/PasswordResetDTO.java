package com.norm.timemall.app.base.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Data
@Accessors(chain = true)
public class PasswordResetDTO {
    @Length(message = "password length must in range {min}-{max}",min = 1,max = 20)
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "emailOrPhone is required")
    private String emailOrPhone;
    @NotBlank(message = "qrcode is required")
    private String qrcode;
}
