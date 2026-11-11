package com.norm.timemall.app.pod.service;

import com.norm.timemall.app.base.pojo.FetchCellPlanOrderDeliver;
import com.norm.timemall.app.base.pojo.dto.DeliverLeaveMsgDTO;
import com.norm.timemall.app.pod.domain.dto.PodPutCellPlanDeliverTagDTO;
import org.springframework.stereotype.Service;

@Service
public interface PodCellPlanOrderDeliverService {
    FetchCellPlanOrderDeliver findCellPlanOrderDeliver(String id);

    void leaveMessage(DeliverLeaveMsgDTO dto);

    void modifyDeliverTag(PodPutCellPlanDeliverTagDTO dto);
    void pay(String orderId);
}
