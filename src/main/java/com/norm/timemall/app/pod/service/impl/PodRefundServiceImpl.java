package com.norm.timemall.app.pod.service.impl;

import com.norm.timemall.app.base.enums.RefundSceneEnum;
import com.norm.timemall.app.pod.domain.dto.PodRefundDTO;
import com.norm.timemall.app.pod.handler.PodCellPlanOrderRefundHandler;
import com.norm.timemall.app.pod.handler.PodPhysicalOrderRefundHandler;
import com.norm.timemall.app.pod.service.PodRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PodRefundServiceImpl implements PodRefundService {
    @Autowired
    private PodCellPlanOrderRefundHandler podCellPlanOrderRefundHandler;
    @Autowired
    private PodPhysicalOrderRefundHandler podPhysicalOrderRefundHandler;
    @Override
    public void refund(PodRefundDTO dto) {
      if(dto.getScene().equals(RefundSceneEnum.CELL_PLAN_ORDER.name())){
          podCellPlanOrderRefundHandler.doRefund(dto.getParam());
      }
      if(dto.getScene().equals(RefundSceneEnum.PHYSICAL_ORDER.name())){
          podPhysicalOrderRefundHandler.doRefund(dto.getParam());
      }
    }
}
