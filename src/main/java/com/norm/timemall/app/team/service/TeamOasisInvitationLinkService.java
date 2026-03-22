package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OasisInvitationLink;
import com.norm.timemall.app.team.domain.dto.TeamCreateOasisInvitationLinkDTO;
import com.norm.timemall.app.team.domain.dto.TeamQueryInvitationLinkInfoDTO;
import com.norm.timemall.app.team.domain.dto.TeamQueryInvitationLinkPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamQueryInvitationLinkInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamQueryInvitationLinkPageRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamOasisInvitationLinkService {
    void createInvitationLink(TeamCreateOasisInvitationLinkDTO dto);

    IPage<TeamQueryInvitationLinkPageRO> queryInvitationLink(TeamQueryInvitationLinkPageDTO dto);
    
    TeamQueryInvitationLinkInfoRO queryInvitationLinkInfo(TeamQueryInvitationLinkInfoDTO dto);

    OasisInvitationLink findOneInvitationLink(String id);

    void delInvitationLink(String id);
    
    void autoIncrementUsageCount(String id);
}
