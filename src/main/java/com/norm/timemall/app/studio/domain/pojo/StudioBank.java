package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class StudioBank {
    @NotBlank(message = "cardholder is required")
    private String cardholder;

    @NotBlank(message = "cardNo is required")
    private String cardNo;
}
