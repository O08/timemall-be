package com.norm.timemall.app.pod.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class PodBillPageDTO extends PageDTO {
    @NotBlank(message = "code is required")
    private String code;
    private String q;
    private String categories;
}
