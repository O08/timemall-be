package com.norm.timemall.app.affiliate.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DelChannelDTO {
    @NotBlank(message = "outreachChannelId required")
    private String outreachChannelId;
}
