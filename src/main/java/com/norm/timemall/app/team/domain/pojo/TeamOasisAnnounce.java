package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;

@Data
public class TeamOasisAnnounce {
    private String title;
    private String avatar;
    private String subTitle;
    private String announceUrl;
    private TeamRiskEntry[] risk;
}
