package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
public class StudioChangeProposalDTO {
    @NotBlank(message = "projectName required")
    @Length(message = "projectName length must in range {min}-{max}",min = 1,max = 40)
    private String projectName;

    @NotNull(message = "starts required")
    private Date starts;
    @NotNull(message = "ends required")
    private Date ends;

    @NotBlank(message = "services required")
    @Length(message = "services length must in range {min}-{max}",min = 1,max = 5000)
    private String services;

    @Length(message = "extraContent length must in range {min}-{max}",min = 0,max = 11000)
    private String extraContent;

    @NotBlank(message = "id required")
    private String id;
}
