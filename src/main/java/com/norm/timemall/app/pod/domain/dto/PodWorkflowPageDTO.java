package com.norm.timemall.app.pod.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class PodWorkflowPageDTO extends PageDTO {
    private String code;
    private String q;
}
