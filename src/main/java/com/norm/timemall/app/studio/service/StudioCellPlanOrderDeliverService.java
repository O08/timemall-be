package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.base.pojo.FetchCellPlanOrderDeliver;
import com.norm.timemall.app.base.pojo.dto.DeliverLeaveMsgDTO;
import org.springframework.stereotype.Service;

@Service
public interface StudioCellPlanOrderDeliverService {


    void newDeliver(String orderId, String previewUri, String deliverUri, String previewName, String deliverName);


    FetchCellPlanOrderDeliver findBrandCellPlanOrderDeliver(String orderId);
}
