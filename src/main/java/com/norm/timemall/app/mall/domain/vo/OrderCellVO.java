package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;

@Data
public class OrderCellVO extends CodeVO {
    private String orderId;
}
