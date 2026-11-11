package com.norm.timemall.app.marketing.domain.ro;

import lombok.Data;

@Data
public class MktFetchRedeemHistoryPageRO {
    private String claimAt;
    private String giftCode;
    private String kolMessage;
    private String pointAmount;
    private String rewardType;
    private String vipDays;
}
