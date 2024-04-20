package com.norm.timemall.app.base.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BaseFetchCodeMappingDTO {
    @NotBlank(message = "codeType is required")
    private String codeType;
    private String itemCode;
}
