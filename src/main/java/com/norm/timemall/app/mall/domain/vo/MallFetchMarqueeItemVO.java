package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.pojo.MallFetchMarqueeItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MallFetchMarqueeItemVO extends CodeVO {
    private MallFetchMarqueeItem marquee;

}
