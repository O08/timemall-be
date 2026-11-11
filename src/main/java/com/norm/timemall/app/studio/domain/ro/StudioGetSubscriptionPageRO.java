package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioGetSubscriptionPageRO {
    private String billCalendar;
    private String buyerAvatar;
    private String buyerName;
    private String buyerUserId;
    private String canceledAt;
    private String endsAt;
    private String planName;
    private String planPrice;
    private String productName;
    private String remark;
    private String startsAt;
    private String status;
    private String trialPeriodEndAt;
    private String trialPeriodStartAt;
}
