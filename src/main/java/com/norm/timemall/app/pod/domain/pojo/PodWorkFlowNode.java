package com.norm.timemall.app.pod.domain.pojo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class PodWorkFlowNode {
    @NotNull(message = "millstones node required")
    @Valid
    private PodMillStoneNode[] millstones;

}
