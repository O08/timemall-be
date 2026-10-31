package com.norm.timemall.app.mall.domain.dto;

import com.norm.timemall.app.mall.domain.pojo.MallOrderPhysicalProductItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class MallOrderPhysicalProductDTO {

    @NotEmpty(message = "您的购物车内还没有商品，请先挑选商品再结算吧~")
    @Valid
    @Size(min = 1,max = 50)
    private MallOrderPhysicalProductItem[] items;

    @NotBlank(message = "recipient required")
    @Length(message = "recipient range in {min}-{max}",min = 1,max = 16)
    private String recipient;

    @NotBlank(message = "recipientPhone required")
    @Length(message = "recipientPhone range in {min}-{max}",min = 1,max = 20)
    private String recipientPhone;

    @NotBlank(message = "shippingAddress required")
    @Length(message = "shippingAddress range in {min}-{max}",min = 1,max = 100)
    private String shippingAddress;

}
