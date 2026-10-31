package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StudioPhysicalOrderDeliveryDTO {

    @Length(message = "courierPhone range in {min}-{max}",min = 0,max = 20)
    private String courierPhone;

    @NotBlank(message = "orderId required")
    private String orderId;

    @Length(message = "shippingMethod range in {min}-{max}",min = 0,max = 20)
    private String shippingMethod;

    @Length(message = "shippingNote range in {min}-{max}",min = 0,max = 100)
    private String shippingNote;

    @Length(message = "shippingNumber range in {min}-{max}",min = 0,max = 50)
    private String shippingNumber;
}
