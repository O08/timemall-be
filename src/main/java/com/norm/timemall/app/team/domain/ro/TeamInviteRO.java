package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamInviteRO {
    private String id;
    private String avatar;
    private String title;
    private String oasisId;
    private String subTitle;
    private String initiator;
    private String membership;
    private String maxMembers;
}
