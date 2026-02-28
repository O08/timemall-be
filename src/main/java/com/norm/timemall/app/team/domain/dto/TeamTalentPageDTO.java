package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class TeamTalentPageDTO extends PageDTO {
    // search keyword
    private String q;
    private String freeNightCounsellor;
    private String hiring;
    private String availableForWork;
    private String supportFreeCooperation;
    private String loginInWeek;
    private String nearby;
    private String location;
}
