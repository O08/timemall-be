package com.norm.timemall.app.pod.domain.ro;

import lombok.Data;

@Data
public class PodFetchMentorPageRO {
    private String id;
    private String mentorName;
    private String mentorUserId;
    private String mentorAvatar;
    private String guidancePeriodEarning;
    private String guidancePeriodInfluencers;
    private String guidancePeriodMessages;
    private String pastYearMessages;
    private String status;
    private String createAt;
}
