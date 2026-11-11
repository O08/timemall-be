package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.pojo.MallCellPricing;
import lombok.Data;

@Data
public class CellPricingVO extends CodeVO {
    private MallCellPricing pricing;
}
