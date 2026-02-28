package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamMembershipOrderRefundDTO {
    @NotBlank(message = "orderId required")
    private String orderId;

    @NotBlank(message = "term required")
    private String term;
}
