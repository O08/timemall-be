package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchCellPlanOrderRO;
import lombok.Data;

@Data
public class StudioFetchCellPlanOrderVO extends CodeVO {
    private StudioFetchCellPlanOrderRO order;
}
