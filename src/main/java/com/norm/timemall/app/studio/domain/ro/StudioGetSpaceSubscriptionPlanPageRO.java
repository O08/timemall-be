package com.norm.timemall.app.studio.domain.ro;

import com.norm.timemall.app.studio.domain.pojo.SubsPlanFeatureItem;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioGetSpaceSubscriptionPlanPageRO {
    private ArrayList<SubsPlanFeatureItem> features;
    private String gracePeriod;
    private String planId;
    private String planName;
    private String price;
    private String productCode;
    private String productDesc;
    private String productName;
    private String sales;
    private String status;
    private String trialPeriod;
}
