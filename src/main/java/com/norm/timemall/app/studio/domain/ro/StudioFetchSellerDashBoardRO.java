package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioFetchSellerDashBoardRO {
    private String balance;
    private String wealthLevel;
    private String employees;
    private String dspCasesSixMonth;
    private String cellEarningsFromRepeatBuyers;
    private String cellRepeatBuyers;
    private String cellTotalBuyers;
    private String earnedMonth;
    private String earningsToDate;
    private String expensesToDate;
    private String pendingPlanPayment;
    private String pendingVirtualPayment;
    private String planEarningsFromRepeatBuyers;
    private String planRepeatBuyers;
    private String planTotalBuyers;
    private String virtualEarningsFromRepeatBuyers;
    private String virtualRepeatBuyers;
    private String virtualTotalBuyers;
    private String onlineTimeSecondsToday;
}
