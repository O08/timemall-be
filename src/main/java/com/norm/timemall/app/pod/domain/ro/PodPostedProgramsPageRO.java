package com.norm.timemall.app.pod.domain.ro;

import lombok.Data;

import java.util.List;

@Data
public class PodPostedProgramsPageRO {
    private String applys;
    private String attendees;
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
