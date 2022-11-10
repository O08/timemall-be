package com.norm.timemall.app.pod.domain.dto;

import com.norm.timemall.app.pod.domain.pojo.PodWorkFlowNode;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class PodModifyWorkflowDTO {
    @NotNull(message = "workflow node required")
    @Valid
    private PodWorkFlowNode workflow;
}
