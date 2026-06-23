package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioFetchBrandMenteeInfoPageRO {
    private String id;
    private String menteeName;
    private String menteeUserId;
    private String menteeAvatar;
    private String pastYearMessages;
    private String guidancePeriodEarning;
    private String guidancePeriodInfluencers;
    private String guidancePeriodMessages;
    private String status;
    private String createAt;
}
