package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamOpenFetchBrandRoleInfoDTO {

    @NotBlank(message = "channel required")
    private String channel;
    @NotBlank(message = "brandId required")
    private String brandId;

}
