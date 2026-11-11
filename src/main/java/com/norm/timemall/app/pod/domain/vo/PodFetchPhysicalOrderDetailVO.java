package com.norm.timemall.app.pod.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.pod.domain.ro.PodFetchPhysicalOrderDetailRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PodFetchPhysicalOrderDetailVO extends CodeVO {
    private PodFetchPhysicalOrderDetailRO detail;
}
