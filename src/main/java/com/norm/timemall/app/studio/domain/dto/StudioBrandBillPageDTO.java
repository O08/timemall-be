package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioBrandBillPageDTO extends PageDTO {
    @NotBlank(message = "code required")
    private String code;
    private String q;
    private String categories;
}
