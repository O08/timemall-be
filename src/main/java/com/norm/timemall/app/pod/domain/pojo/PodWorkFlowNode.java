package com.norm.timemall.app.pod.domain.pojo;

import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Data
public class PodWorkFlowNode {
    @NotNull(message = "millstones node required")
    @Valid
    private PodMillStoneNode[] millstones;
    private PodWorkflowServiceInfo serviceInfo;
    private String doneStageNo;

}
