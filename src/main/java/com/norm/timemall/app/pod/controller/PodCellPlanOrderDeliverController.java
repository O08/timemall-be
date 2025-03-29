package com.norm.timemall.app.pod.controller;


import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.pojo.FetchCellPlanOrderDeliver;
import com.norm.timemall.app.base.pojo.vo.CellPlanOrderDeliverVO;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.pod.domain.dto.PodPutCellPlanDeliverTagDTO;
import com.norm.timemall.app.pod.service.PodApiAccessControlService;
import com.norm.timemall.app.base.pojo.dto.DeliverLeaveMsgDTO;
import com.norm.timemall.app.pod.service.PodCellPlanOrderDeliverService;
import com.norm.timemall.app.pod.service.PodCellPlanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class PodCellPlanOrderDeliverController {
    @Autowired
    private OrderFlowService orderFlowService;

    @Autowired
   private PodCellPlanOrderService podCellPlanOrderService;

    @Autowired
    private PodCellPlanOrderDeliverService podCellPlanOrderDeliverService;
    @Autowired
    private PodApiAccessControlService podApiAccessControlService;
    @GetMapping("/api/v1/web_epod/cell/plan_order/{id}/deliver")
    public CellPlanOrderDeliverVO fetchMpsPaperDeliver(@PathVariable("id") String id){

        FetchCellPlanOrderDeliver deliver = podCellPlanOrderDeliverService.findCellPlanOrderDeliver(id);
        CellPlanOrderDeliverVO vo = new CellPlanOrderDeliverVO();
        vo.setDeliver(deliver);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @PutMapping("/api/v1/web_epod/cell/plan_order/deliver/leave_a_message")
    public SuccessVO leaveMessage(@RequestBody @Validated DeliverLeaveMsgDTO dto){

        podCellPlanOrderDeliverService.leaveMessage(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/web_epod/cell/plan_order/deliver/tag")
    public SuccessVO tagPaperDeliver(@RequestBody @Validated PodPutCellPlanDeliverTagDTO dto){

        boolean checked =podApiAccessControlService.isCellPlanOrderDeliverReceiver(dto.getOrderId(),CellPlanOrderTagEnum.DELIVERING.ordinal()+"");
        if(!checked){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // if tag to revision
        if(checked && DeliverTagEnum.REVISION.getMark().equals(dto.getTag())){
            podCellPlanOrderDeliverService.modifyDeliverTag(dto);
        }
        // if tag to delivered
        if(checked && DeliverTagEnum.DELIVERED.getMark().equals(dto.getTag())){
            try {

                orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                        TransTypeEnum.PLAN_ORDER_TRANSFER_TO_BRAND.getMark());
                // transfer to supplier when plan order finish.
                podCellPlanOrderDeliverService.pay(dto.getOrderId());

                // update deliver tag
                podCellPlanOrderDeliverService.modifyDeliverTag(dto);

            }finally {

                orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                        TransTypeEnum.PLAN_ORDER_TRANSFER_TO_BRAND.getMark());

            }

        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
