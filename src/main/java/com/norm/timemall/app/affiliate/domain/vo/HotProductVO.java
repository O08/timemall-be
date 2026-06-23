package com.norm.timemall.app.affiliate.domain.vo;

import com.norm.timemall.app.affiliate.domain.pojo.HotProduct;
import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HotProductVO extends CodeVO {
    private HotProduct product;
}
