package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.dto.TeamDeliverLeaveMsgDTO;
import com.norm.timemall.app.team.domain.dto.TeamPutCommissionDeliverTagDTO;
import com.norm.timemall.app.team.domain.pojo.TeamFetchCommissionDeliver;
import org.springframework.stereotype.Service;

@Service
public interface TeamCommissionDeliverService {
    void newDeliver(String commissionId, String previewUri, String deliverUri, String previewName, String deliverName);


    TeamFetchCommissionDeliver findCommissionDeliver(String role,String commissionId);

    void leaveMessage(TeamDeliverLeaveMsgDTO dto);

    void modifyCommissionDeliverTag(TeamPutCommissionDeliverTagDTO dto);

}
