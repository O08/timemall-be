package com.norm.timemall.app.studio.handler;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.BluvarrierRoleEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.SubsBillStatusEnum;
import com.norm.timemall.app.base.enums.WhereStoreMoneyEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseBluvarrierMapper;
import com.norm.timemall.app.base.mo.Bluvarrier;
import com.norm.timemall.app.base.mo.SubsBill;
import com.norm.timemall.app.base.pojo.RefundBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.studio.domain.pojo.StudioSubscriptionBillRefundHandlerParam;
import com.norm.timemall.app.studio.mapper.StudioSubsBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class StudioSubscriptionBillRefundHandler {
    @Autowired
    private DefaultPayService defaultPayService;
    @Autowired
    private StudioSubsBillMapper studioSubsBillMapper;
    @Autowired
    private BaseBluvarrierMapper baseBluvarrierMapper;
    public void doRefund(String param){
        StudioSubscriptionBillRefundHandlerParam handlerParam = new Gson().fromJson(param, StudioSubscriptionBillRefundHandlerParam.class);
        if(handlerParam==null || CharSequenceUtil.isBlank(handlerParam.getBillId()) ){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(CharSequenceUtil.isBlank(handlerParam.getTerm()) || !"我支持退款".equals(handlerParam.getTerm())){
            throw new QuickMessageException("未进行退款授权,操作失败");
        }
        SubsBill bill = studioSubsBillMapper.selectById(handlerParam.getBillId());
        if(bill ==null){
            throw new QuickMessageException("未找到相关账单,操作失败");
        }
        // role validated
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller= currentUserBrandId.equals(bill.getSellerBrandId());

        LambdaQueryWrapper<Bluvarrier> lambdaQueryWrapper= Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Bluvarrier::getRoleCode, BluvarrierRoleEnum.HOSTING.getMark());
        Bluvarrier bluvarrier = baseBluvarrierMapper.selectOne(lambdaQueryWrapper);
        boolean isBluvarrier = ObjectUtil.isNotNull(bluvarrier) && currentUserBrandId.equals(bluvarrier.getBrandId());

        if(!(isSeller || isBluvarrier)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // status validate
        // refunded validated
        if(SubsBillStatusEnum.REFUNDED.getMark().equals(bill.getStatus())){
            throw new ErrorCodeException(CodeEnum.REPEAT_REQUEST_REFUND);
        }
        if(!SubsBillStatusEnum.PAID.getMark().equals(bill.getStatus())){
            throw new ErrorCodeException(CodeEnum.UN_SUPPORT_REFUND_ACTION);
        }
        // remittance validated
        if(WhereStoreMoneyEnum.SELLER.getMark().equals(bill.getWhereStoreMoney())){
            throw new ErrorCodeException(CodeEnum.UN_SUPPORT_REFUND_ACTION);
        }
        if(bill.getNetIncome().compareTo(BigDecimal.ZERO)<=0){
            throw new ErrorCodeException(CodeEnum.UN_SUPPORT_REFUND_ACTION);
        }

        // refund
        RefundBO bo = new RefundBO();
        bo.setOutNo(bill.getTransNo())
                .setTradeNo(bill.getTradeNo());
        defaultPayService.refund(new Gson().toJson(bo));

        // update order as refunded
        bill.setStatus(SubsBillStatusEnum.REFUNDED.getMark());
        bill.setModifiedAt(new Date());
        bill.setWhereStoreMoney(WhereStoreMoneyEnum.BUYER.getMark());
        studioSubsBillMapper.updateById(bill);

    }
}
