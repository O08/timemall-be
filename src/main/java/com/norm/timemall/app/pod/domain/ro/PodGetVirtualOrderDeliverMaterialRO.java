package com.norm.timemall.app.pod.domain.ro;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PodGetVirtualOrderDeliverMaterialRO {
    private String deliverAttachment;
    private String deliverNote;
    private String productName;
    private String pack;
    private String shippingMethod;
}
