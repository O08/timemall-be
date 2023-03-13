package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class TeamCommissionDTO extends PageDTO  {
    // search keyword
    private String q;
    private String tag;
    private String sort;
}
