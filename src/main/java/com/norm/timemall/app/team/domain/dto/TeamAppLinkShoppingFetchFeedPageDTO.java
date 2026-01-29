package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppLinkShoppingFetchFeedPageDTO extends PageDTO {
    private String q;
    @NotBlank(message = "sort required")
    private String sort;

    @NotBlank(message = "channel required")
    private String channel;
}
