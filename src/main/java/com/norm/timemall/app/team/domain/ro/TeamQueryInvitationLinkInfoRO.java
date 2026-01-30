package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamQueryInvitationLinkInfoRO {
    private String grantedRole;
    private String inviterAvatar;
    private String inviterName;
    private String linkId;
    private String members;
    private String oasisCreateAt;
    private String oasisDescription;
    private String oasisName;
    private String oasisLogo;
    private String oasisStatus;
    private String expireTime;
}
