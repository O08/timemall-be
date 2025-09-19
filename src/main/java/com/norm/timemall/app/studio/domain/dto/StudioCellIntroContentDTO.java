package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Data
public class StudioCellIntroContentDTO {


    @NotNull(message = "id required")
    private String id;

    @NotNull(message = "productDesc required")
    @Length(message = "productDesc length must in range {min}-{max}",min = 1,max = 5000)
    private String productDesc;
}
