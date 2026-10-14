package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioFetchCandidateSupplierDTO {
    @NotBlank(message = "q parameter cannot be empty")
    private String q;
}