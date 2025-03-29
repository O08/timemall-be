package com.norm.timemall.app.affiliate.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DelPpcLinkDTO {
    @NotBlank(message = "id required")
    private String id;
}
