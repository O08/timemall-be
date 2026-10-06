package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.studio.domain.pojo.StudioBank;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Data
public class StudioBrandBankDTO {
    @NotNull(message = "bank node required")
    @Valid
    private StudioBank bank;
}
