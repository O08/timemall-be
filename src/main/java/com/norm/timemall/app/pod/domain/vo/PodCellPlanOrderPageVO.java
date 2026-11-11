package com.norm.timemall.app.pod.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.pod.domain.ro.PodCellPlanOrderPageRO;
import lombok.Data;

@Data
public class PodCellPlanOrderPageVO extends CodeVO {
    private IPage<PodCellPlanOrderPageRO> planOrder;
}
