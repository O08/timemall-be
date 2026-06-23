package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.pojo.FetchCellPlan;
import lombok.Data;

@Data
public class FetchCellPlanVO extends CodeVO {
    private FetchCellPlan plan;
}
