package com.norm.timemall.app.pod.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

@Data
public class PodBillPageDTO extends PageDTO {
    @NotEmpty(message = "code is required")
    private String code;
    private String q;
}
