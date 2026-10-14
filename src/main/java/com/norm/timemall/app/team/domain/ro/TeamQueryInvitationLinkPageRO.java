package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamQueryInvitationLinkPageRO {
    private String id;
    private String invitationCode;
    private String expireTime;
    private String grantedOasisRoleName;
    private String maxUses;
    private String usageCount;
    private String createAt;

}
