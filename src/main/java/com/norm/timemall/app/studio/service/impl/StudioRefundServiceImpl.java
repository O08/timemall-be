package com.norm.timemall.app.studio.service.impl;

import com.norm.timemall.app.base.enums.RefundSceneEnum;
import com.norm.timemall.app.studio.domain.dto.StudioRefundDTO;
import com.norm.timemall.app.studio.handler.StudioSubscriptionBillRefundHandler;
import com.norm.timemall.app.studio.handler.StudioVirtualProductOrderRefundHandler;
import com.norm.timemall.app.studio.service.StudioRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioRefundServiceImpl implements StudioRefundService {
    @Autowired
    private StudioVirtualProductOrderRefundHandler studioVirtualProductOrderRefundHandler;
    @Autowired
    private StudioSubscriptionBillRefundHandler studioSubscriptionBillRefundHandler;
    @Override
    public void refund(StudioRefundDTO dto) {
        if(dto.getScene().equals(RefundSceneEnum.VIRTUAL_ORDER.name())){
            studioVirtualProductOrderRefundHandler.doRefund(dto.getParam());
        }
        if(dto.getScene().equals(RefundSceneEnum.SUBSCRIPTION.name())){
            studioSubscriptionBillRefundHandler.doRefund(dto.getParam());
        }
    }
}
