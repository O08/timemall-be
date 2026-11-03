package com.norm.timemall.app.pod.domain.ro;

import lombok.Data;

@Data
public class PodGetSubscriptionPageRO {
    private String billCalendar;
    private String planId;
    private String planName;
    private String planPrice;
    private String productCode;
    private String productName;
    private String recentPlanPrice;
    private String remark;
    private String sellerAvatar;
    private String sellerHandle;
    private String sellerName;
    private String sellerUserId;
    private String startsAt;
    private String endsAt;
    private String trialPeriodStartAt;
    private String trialPeriodEndAt;
    private String status;
    private String subscriptionId;
}
