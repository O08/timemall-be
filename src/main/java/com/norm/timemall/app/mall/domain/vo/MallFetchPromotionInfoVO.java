package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPromotionInfoRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MallFetchPromotionInfoVO extends CodeVO {


    private MallFetchPromotionInfoRO promotion;
}
