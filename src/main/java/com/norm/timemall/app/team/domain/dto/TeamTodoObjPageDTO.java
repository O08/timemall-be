package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamTodoObjPageDTO extends PageDTO {

    @NotBlank(message = "brandId required")
    private String brandId;
}
