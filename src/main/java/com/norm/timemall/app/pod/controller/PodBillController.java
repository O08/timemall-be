package com.norm.timemall.app.pod.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.DataPolicyService;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.pod.domain.dto.PodBillPageDTO;
import com.norm.timemall.app.pod.domain.ro.FetchBillDetailRO;
import com.norm.timemall.app.pod.domain.ro.PodBillsRO;
import com.norm.timemall.app.pod.domain.vo.FetchBillDetailVO;
import com.norm.timemall.app.pod.domain.vo.PodBillsPageVO;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.pod.service.PodBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 账单
 */
@RestController
public class PodBillController {

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private PodBillService podBillService;

    @Autowired
    private DataPolicyService dataPolicyService;

    @Autowired
    private OrderFlowService orderFlowService;



    /*
     * 分页查询用户帐单
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_epod/me/bill")
    public PodBillsPageVO retrieveBills(@Validated PodBillPageDTO dto, @AuthenticationPrincipal CustomizeUser user)
    {
        IPage<PodBillsRO> bills = podBillService.findBills(dto,user);
        PodBillsPageVO billsPageVO = new PodBillsPageVO();
        billsPageVO.setResponseCode(CodeEnum.SUCCESS);
        billsPageVO.setBills(bills);
        return billsPageVO;
    }

    /**
     * 特约账单支付
     * @param billId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/web_epod/bill/millstone/pay/{bill_id}")
    public SuccessVO payBill(@PathVariable("bill_id") String billId){

        try {

            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.MILLSTONE_BILL_PAY.getMark());
            podBillService.pay(billId);

        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.MILLSTONE_BILL_PAY.getMark());
        }
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @GetMapping("/api/v1/web_epod/bill/{id}/detail")
    public FetchBillDetailVO fetchBillDetail(@PathVariable("id") String id){

        FetchBillDetailRO detailRO = podBillService.findbillDetail(id);
        FetchBillDetailVO vo=new FetchBillDetailVO();
        vo.setDetail(detailRO);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }


}
