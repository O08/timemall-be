package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.AppSortDirectionEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppRedeemSortGenreDTO {
    @NotBlank(message = "genreId required")
    private String genreId;

    @NotBlank(message = "direction required")
    @EnumCheck(enumClass = AppSortDirectionEnum.class,message = "field: direction, incorrect parameter value ,option: up; down;")
    private String direction;
}
