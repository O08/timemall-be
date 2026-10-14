package com.norm.timemall.app.pod.domain.dto;

import com.norm.timemall.app.pod.domain.pojo.PodWorkFlowNode;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Data
public class PodModifyWorkflowDTO {
    @NotNull(message = "workflow node required")
    @Valid
    private PodWorkFlowNode workflow;
}
