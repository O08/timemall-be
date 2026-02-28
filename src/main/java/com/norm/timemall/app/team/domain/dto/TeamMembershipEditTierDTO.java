package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class TeamMembershipEditTierDTO {
    @NotBlank(message = "tierId required")
    private String tierId;

    @NotBlank(message = "subscribeRoleId required")
    private String subscribeRoleId;

    @NotBlank(message = "tierName required")
    @Length(message = "tierName length must in range {min}-{max}",min = 1,max = 72)
    private String tierName;

    @NotNull(message = "price required")
    @Positive(message = "price required and must be positive")
    private BigDecimal price;

    @NotBlank(message = "tierDescription required")
    @Length(message = "tierDescription length must in range {min}-{max}",min = 1,max = 200)
    private String tierDescription;
}
