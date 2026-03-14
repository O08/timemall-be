package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.pojo.MallFetchMarqueeCell;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MallFetchMarqueeCellVO extends CodeVO {
    private MallFetchMarqueeCell cell;
}
