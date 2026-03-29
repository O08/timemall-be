package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class TeamMembershipFetchOpenTierOrderPageDTO extends PageDTO {
    private String oasisId;
    private String q;
}
