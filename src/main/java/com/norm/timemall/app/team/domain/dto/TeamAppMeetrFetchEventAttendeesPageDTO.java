package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamAppMeetrFetchEventAttendeesPageDTO extends PageDTO {
    @NotBlank(message = "eventId is required")
    private String eventId;
    private String q;
}
