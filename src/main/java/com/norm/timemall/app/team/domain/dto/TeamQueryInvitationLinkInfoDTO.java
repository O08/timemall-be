package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamQueryInvitationLinkInfoDTO {
    @NotBlank(message = "invitationCode required")
    private String invitationCode;
}
