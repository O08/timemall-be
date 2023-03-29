package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class TeamSwapCellDTO {
    @NotBlank(message = "sponsor required")
    private String sponsor;
    @NotBlank(message = "sponsorCellId required")
    private String sponsorCellId;
    @NotNull(message = "sponsorCellQuantity required")
    @Positive(message = "sponsorCellQuantity required must be positive")
    private int sponsorCellQuantity;
    @NotBlank(message = "sponsorCellSbu required")
    private String sponsorCellSbu;
    @NotBlank(message = "partner required")
    private String partner;
    @NotBlank(message = "partnerCellId required")
    private String partnerCellId;
    @NotNull(message = "partnerCellQuantity required")
    @Positive(message = "partnerCellQuantity required and must be positive")
    private int partnerCellQuantity;
    @NotBlank(message = "partnerCellSbu required")
    private String partnerCellSbu;

}
