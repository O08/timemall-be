package com.norm.timemall.app.pod.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;

import lombok.Data;

@Data
public class PodGetSubscriptionPageDTO extends PageDTO {
    private String q;

    private String tag;
}
