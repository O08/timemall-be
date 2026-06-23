package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.FetchCellCouponBenefitRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FetchCellCouponBenefitVO extends CodeVO {
    private FetchCellCouponBenefitRO benefit;
}
