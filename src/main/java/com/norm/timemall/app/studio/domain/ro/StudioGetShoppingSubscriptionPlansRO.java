package com.norm.timemall.app.studio.domain.ro;

import com.norm.timemall.app.studio.domain.pojo.StudioGetShoppingSubscriptionPlansOfferItem;
import com.norm.timemall.app.studio.domain.pojo.SubsPlanFeatureItem;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioGetShoppingSubscriptionPlansRO {
    private ArrayList<SubsPlanFeatureItem> features;
    private String gracePeriod;
    private String id;
    private ArrayList<StudioGetShoppingSubscriptionPlansOfferItem> offers;
    private String planName;
    private String planType;
    private String price;
    private String productDesc;
    private String planDesc;
    private String productId;
    private String productName;
    private String sales;
    private String status;
    private String trialPeriod;
    private String sellerName;
    private String sellerAvatar;
    private String sellerHandle;
    private String sellerBrandId;
}
