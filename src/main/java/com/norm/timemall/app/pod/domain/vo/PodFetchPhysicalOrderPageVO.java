package com.norm.timemall.app.pod.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.pod.domain.ro.PodFetchPhysicalOrderPageRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PodFetchPhysicalOrderPageVO extends CodeVO {
    private IPage<PodFetchPhysicalOrderPageRO> order;
}
