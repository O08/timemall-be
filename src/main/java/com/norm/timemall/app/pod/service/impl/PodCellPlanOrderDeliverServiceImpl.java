package com.norm.timemall.app.pod.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.AffiliateOrderTypeEnum;
import com.norm.timemall.app.base.enums.CellPlanOrderTagEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AffiliateOrder;
import com.norm.timemall.app.base.pojo.FetchCellPlanOrderDeliver;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.base.pojo.dto.DeliverLeaveMsgDTO;
import com.norm.timemall.app.base.pojo.ro.FetchCellPlanOrderDeliverRO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.pod.domain.dto.PodPutCellPlanDeliverTagDTO;
import com.norm.timemall.app.pod.domain.pojo.PodBrandAndPriceBO;
import com.norm.timemall.app.pod.mapper.PodAffiliateOrderMapper;
import com.norm.timemall.app.pod.mapper.PodCellPlanDeliverMapper;
import com.norm.timemall.app.pod.mapper.PodCellPlanOrderMapper;
import com.norm.timemall.app.pod.service.PodAffiliatePayService;
import com.norm.timemall.app.pod.service.PodCellPlanOrderDeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@Service
public class PodCellPlanOrderDeliverServiceImpl implements PodCellPlanOrderDeliverService {
    @Autowired
    private PodCellPlanDeliverMapper podCellPlanDeliverMapper;
    @Autowired
    private DefaultPayService defaultPayService;

    @Autowired
    private PodCellPlanOrderMapper podCellPlanOrderMapper;

    @Autowired
    private PodAffiliatePayService podAffiliatePayService;

    @Autowired
    private PodAffiliateOrderMapper podAffiliateOrderMapper;



    @Override
    public FetchCellPlanOrderDeliver findCellPlanOrderDeliver(String id) {

        ArrayList<FetchCellPlanOrderDeliverRO> records = podCellPlanDeliverMapper.selectCellPlanOrderDeliverByOrderId(id);
        FetchCellPlanOrderDeliver deliver = new FetchCellPlanOrderDeliver();
        deliver.setRecords(records);
        return deliver;

    }

    @Override
    public void leaveMessage(DeliverLeaveMsgDTO dto) {
        podCellPlanDeliverMapper.updateMsgById(dto);
    }

    @Override
    public void modifyDeliverTag(PodPutCellPlanDeliverTagDTO dto) {
        podCellPlanDeliverMapper.updateTagById(dto);
    }

    @Override
    public void pay(String orderId) {
        PodBrandAndPriceBO planInfo =  podCellPlanOrderMapper.selectBrandAndAmountById(orderId);

        //cal commission and netIncome
        LambdaQueryWrapper<AffiliateOrder> affiliateOrderLambdaQueryWrapper= Wrappers.lambdaQuery();
        affiliateOrderLambdaQueryWrapper.eq(AffiliateOrder::getOrderId,orderId)
                .eq(AffiliateOrder::getOrderType, AffiliateOrderTypeEnum.PLAN.getMark());
        AffiliateOrder affiliateOrder = podAffiliateOrderMapper.selectOne(affiliateOrderLambdaQueryWrapper);
        BigDecimal commission=BigDecimal.ZERO;
        if(ObjectUtil.isNotNull(affiliateOrder)){
            commission=affiliateOrder.getRevshare().multiply(planInfo.getRevenue()).divide(new BigDecimal(100),2, RoundingMode.HALF_UP);
        }
        BigDecimal netIncome=planInfo.getRevenue().subtract(commission);

        TransferBO bo=generateTransferBO(netIncome,planInfo.getBrandId(),orderId);
        defaultPayService.transfer(new Gson().toJson(bo));

        // update plan  order as finish
        String userId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        podCellPlanOrderMapper.updatePlanInfoByConsumerIdAndId(netIncome,commission,orderId,userId, CellPlanOrderTagEnum.COMPLETED.ordinal());

        // pay affiliate if order belong to affiliate order
        if(ObjectUtil.isNotNull(affiliateOrder)){
            podAffiliatePayService.planOrderReshare(affiliateOrder.getInfluencer(), commission, orderId, planInfo.getBrandId());
        }

    }
    private TransferBO generateTransferBO(BigDecimal amount, String payeeAccount,String orderId){
        TransferBO bo = new TransferBO();
        bo.setAmount(amount)
                .setOutNo(orderId)
                .setPayeeType(FidTypeEnum.BRAND.getMark())
                .setPayeeAccount(payeeAccount)
                .setPayerAccount(OperatorConfig.sysMidFinAccount)
                .setPayerType(FidTypeEnum.OPERATOR.getMark())
                .setTransType(TransTypeEnum.PLAN_ORDER_TRANSFER_TO_BRAND.getMark());
        return  bo;
    }
}
