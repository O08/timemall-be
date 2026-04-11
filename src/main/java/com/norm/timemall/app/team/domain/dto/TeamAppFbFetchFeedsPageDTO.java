package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppFbFetchFeedsPageDTO extends PageDTO {

    private String q;
    @NotBlank(message = "sort required")
    private String sort;
    private String filter;

    @NotBlank(message = "channel required")
    private String channel;

}
