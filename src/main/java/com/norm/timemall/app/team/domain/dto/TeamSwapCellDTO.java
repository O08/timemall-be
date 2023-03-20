package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamSwapCellDTO {
    @NotBlank(message = "sponsor required")
    private String sponsor;
    @NotBlank(message = "sponsorCellId required")
    private String sponsorCellId;
    @NotBlank(message = "sponsorCellQuantity required")
    private int sponsorCellQuantity;
    @NotBlank(message = "sponsorCellSbu required")
    private String sponsorCellSbu;
    @NotBlank(message = "partner required")
    private String partner;
    @NotBlank(message = "partnerCellId required")
    private String partnerCellId;
    @NotBlank(message = "partnerrCellQuantity required")
    private int partnerrCellQuantity;
    @NotBlank(message = "partnerCellSbu required")
    private String partnerCellSbu;

}
