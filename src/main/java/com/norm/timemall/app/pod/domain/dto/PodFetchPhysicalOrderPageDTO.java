package com.norm.timemall.app.pod.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PodFetchPhysicalOrderPageDTO extends PageDTO {
    private String q;
    private String status;
}
