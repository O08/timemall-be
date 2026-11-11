package com.norm.timemall.app.base.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BaseMcpOAuthSessionDTO {

    @NotBlank(message = "key不能为空")
    private String key;


    private String payload;
}
