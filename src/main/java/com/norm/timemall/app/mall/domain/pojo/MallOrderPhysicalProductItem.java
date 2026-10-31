package com.norm.timemall.app.mall.domain.pojo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MallOrderPhysicalProductItem {
    @NotBlank(message = "商品ID不能为空")
    private String productId;

    @NotNull(message = "购买数量不能为空")
    @Min(value = 1, message = "购买数量不合法，最少购买1件")
    @Max(value = 99, message = "单笔订单同款商品最多可购买99件，如需大宗采购请联系商家")
    private Integer quantity;
}
