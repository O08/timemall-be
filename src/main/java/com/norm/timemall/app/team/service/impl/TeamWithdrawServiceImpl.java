package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.FinAccountMarkEnum;
import com.norm.timemall.app.base.enums.WithdrawTagEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.pay.domain.dto.WithDrawDTO;
import com.norm.timemall.app.team.domain.pojo.WithdrawToALiPayBO;
import com.norm.timemall.app.team.mapper.TeamFinAccountMapper;
import com.norm.timemall.app.team.mapper.TeamBrandAlipayMapper;
import com.norm.timemall.app.team.mapper.TeamTransactionsMapper;
import com.norm.timemall.app.team.mapper.TeamWithdrawRecordMapper;
import com.norm.timemall.app.team.service.TeamWithdrawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
public class TeamWithdrawServiceImpl implements TeamWithdrawService {
    @Autowired
    private TeamBrandAlipayMapper teamBrandAlipayMapper;
    @Autowired
    private TeamFinAccountMapper teamFinAccountMapper;

    @Autowired
    private TeamWithdrawRecordMapper teamWithdrawRecordMapper;
    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderFlowService orderFlowService;
    @Override
    public WithdrawToALiPayBO toAliPay(WithDrawDTO dto) {
        log.info("群巅-提现到支付宝"+ JSON.toJSONString(dto));
        WithdrawToALiPayBO bo = new WithdrawToALiPayBO();
        try{
            // orderFlow ctl repeat processing
            orderFlowService.insertOrderFlow(dto.getToAccountId(),WithdrawTagEnum.CREATED.getMark() );
            bo = doToAliPay(dto);
        }finally {
            orderFlowService.deleteOrderFlow(dto.getToAccountId(), WithdrawTagEnum.CREATED.getMark());
        }
        return bo;

    }
    public WithdrawToALiPayBO doToAliPay(WithDrawDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // 查询账户信息 提现金额验证
        FinAccount account = teamFinAccountMapper.selectOneByFid(brandId, FidTypeEnum.BRAND.getMark());
        if(account == null || dto.getAmount().compareTo(account.getDrawable())>0){
            throw  new ErrorCodeException(CodeEnum.NO_SUFFICIENT_FUNDS);
        }
        BigDecimal usedCredit = teamWithdrawRecordMapper.selectUsedCreditIn24Hour(brandId, DateUtil.beginOfDay(new Date()));
        BigDecimal remainingCredit = account.getBankLine() ==null ? BigDecimal.valueOf(500000) :  account.getBankLine().subtract(usedCredit);
        // 限额处理
        if(FinAccountMarkEnum.WITHDRAW_LIMIT.getMark().equals(account.getMark())
                && dto.getAmount().compareTo(remainingCredit)>0){

            throw  new ErrorCodeException(CodeEnum.MAX_LIMIT);
        }
        BrandAlipay brandAlipay = teamBrandAlipayMapper.selectById(dto.getToAccountId());
        if(brandAlipay==null){
            throw  new ErrorCodeException(CodeEnum.USER_ALI_PAY_ACCOUNT_NOT_EXIST);
        }
        // insert record
        WithdrawRecord record = new WithdrawRecord();
        BigDecimal feeRate = BigDecimal.valueOf(0.03); // todo change to config
        record.setId(IdUtil.simpleUUID())
                .setBrandId(brandId)
                .setPayeeType("AliPay")
                .setPayeeAccount(brandAlipay.getPayeeAccount())
                .setPayeeRealName(brandAlipay.getPayeeRealName())
                .setAmount(dto.getAmount())
                .setFeeRate(feeRate)
                .setFinalAmount(dto.getAmount().multiply(BigDecimal.ONE.subtract(feeRate)))
                .setTag(WithdrawTagEnum.CREATED.getMark())
                .setTagDesc(WithdrawTagEnum.CREATED.getDesc())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamWithdrawRecordMapper.insert(record);
        // return bo
        WithdrawToALiPayBO bo = new WithdrawToALiPayBO();
        bo.setTitle("提现");
        bo.setOrderNo(record.getId());
        bo.setPayeeAccount(brandAlipay.getPayeeAccount());
        bo.setPayeeRealName(brandAlipay.getPayeeRealName());
        bo.setAmount(dto.getAmount().multiply(BigDecimal.ONE.subtract(feeRate)));
        return bo;
    }

    @Override
    public void toAliPaySuccess(String orderNo, AlipayFundTransToaccountTransferResponse response) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // update tag and msg
        WithdrawRecord record = teamWithdrawRecordMapper.selectById(orderNo);
        record.setTag(WithdrawTagEnum.SUCCESS.getMark())
                .setTagDesc(WithdrawTagEnum.SUCCESS.getDesc())
                .setMsg(JSON.toJSONString(response))
                .setModifiedAt(new Date());
        teamWithdrawRecordMapper.updateById(record);
        // account option
        teamFinAccountMapper.updateMinusAccountByFid(record.getAmount(),brandId,FidTypeEnum.BRAND.getMark());

    }

    @Override
    public void toAliPayFail(String orderNo, AlipayFundTransToaccountTransferResponse response) {
        // update tag and msg
        WithdrawRecord record = teamWithdrawRecordMapper.selectById(orderNo);
        record.setTag(WithdrawTagEnum.FAIL.getMark())
                .setTagDesc(WithdrawTagEnum.FAIL.getDesc())
                .setMsg(JSON.toJSONString(response))
                .setModifiedAt(new Date());
        teamWithdrawRecordMapper.updateById(record);

    }
}
