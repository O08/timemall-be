package com.norm.timemall.app.pod.domain.ro;

import com.norm.timemall.app.base.pojo.DefaultPhysicalOrderItem;
import lombok.Data;

import java.util.List;

@Data
public class PodFetchPhysicalOrderPageRO {
    private String createAt;
    private List<DefaultPhysicalOrderItem> items;
    private String orderId;
    private String orderStatus;
    private String payAmount;
    private String sellerAvatar;
    private String sellerBrandId;
    private String sellerHandle;
    private String sellerName;
    private String sellerUserId;
}
