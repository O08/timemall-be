package com.norm.timemall.app.pod.domain.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PodMillStoneNode {
    private String end;
    private PodMillStoneEntryNode[] metric;
    private PodMillStoneEntryNode[] objective;
    @NotNull(message = "payrate node required")
    @Positive(message = "payrate is positive")
    private Long payRate;
    private String start;
    private String title;
}
