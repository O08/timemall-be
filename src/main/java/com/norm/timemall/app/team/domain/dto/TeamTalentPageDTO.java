package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class TeamTalentPageDTO extends PageDTO {
    // search keyword
    private String q;
}