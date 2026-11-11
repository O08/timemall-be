package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamOfficeQueryCompensationPageDTO extends PageDTO {
    @NotBlank(message = "oasisId required")
    private String oasisId;
    private String q;
    private String status;

    private String direction;
}
