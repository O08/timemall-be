package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderVirtualProductVO extends CodeVO {

    private String orderId;

}
