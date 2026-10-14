package com.norm.timemall.app.affiliate.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class DelProductFromChoiceDTO {
    @NotBlank(message = "cellId required")
    private String cellId;
}
