package com.norm.timemall.app.pod.domain.ro;

import lombok.Data;

import java.util.List;

@Data
public class PodDiscoveryProgramsPageRO {
    // 是否已申请 ：1-已申请，0-未申请
    private String applied;
    private String applys;
    private String attendees;
    private String authorAvatar;
    private String authorBrandId;
    private String authorIpLocation;
    private String authorName;
    private String authorUserId;
    private String buzz;
    private String createdAt;
    private String description;
    private String onlineLink;
    private String programId;
    private String status;
    private String thumbnail;
    private String title;
    private List<String> topics;
    private String workMode;
}
