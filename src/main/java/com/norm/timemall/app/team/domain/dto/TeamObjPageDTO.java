package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class TeamObjPageDTO extends PageDTO {
    // search keyword
    private String q;
}
