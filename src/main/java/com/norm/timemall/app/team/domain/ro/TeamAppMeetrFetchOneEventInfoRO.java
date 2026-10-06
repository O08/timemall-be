package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

import java.util.List;

@Data
public class TeamAppMeetrFetchOneEventInfoRO {
    private String id;
    private String activityStartAt;
    private String allowGuests;
    private String attendees;
    private String budget;
    private String category;
    private String description;
    private String duration;
    private String durationType;
    private String eventStatus;
    private String eventType;
    private String hostedBrandAvatar;
    private String hostedBrandId;
    private String hostedBrandName;
    private String location;
    private String maxSeats;
    private String onlineLink;
    // 是否预约，0否，1是
    private String reserved;
    private String thumbnail;
    private String title;
    private List<String> topics;
}
