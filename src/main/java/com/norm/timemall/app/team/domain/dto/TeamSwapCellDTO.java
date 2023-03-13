package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

@Data
public class TeamSwapCellDTO {
    private String sponsor;
    private String sponsorCellId;
    private int sponsorCellQuantity;
    private String sponsorCellSbu;
    private String partner;
    private String partnerCellId;
    private int partnerrCellQuantity;
    private String partnerCellSbu;

}
