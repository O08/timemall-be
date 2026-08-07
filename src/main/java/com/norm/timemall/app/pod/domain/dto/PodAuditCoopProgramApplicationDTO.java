package com.norm.timemall.app.pod.domain.dto;

import com.norm.timemall.app.base.enums.CoopApplicationStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PodAuditCoopProgramApplicationDTO {

    @NotBlank(message = "applicationId required")
    private String applicationId;

    @NotNull(message = "status required")
    private CoopApplicationStatusEnum status;
 }
