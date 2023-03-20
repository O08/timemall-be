package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamAddBrandAlipayDTO {
    @NotBlank(message = "payeeAccount required")

    private String payeeAccount;
    @NotBlank(message = "payeeRealName required")
    private String payeeRealName;

}
