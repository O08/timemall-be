package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TeamOwnedObjPageDTO extends PageDTO {
    @NotBlank(message = "mark is required,option: 1-合作意向 2-拥有 3-结束")
    private String mark;
    @NotBlank(message = "brandId required")
    private String brandId;
}
