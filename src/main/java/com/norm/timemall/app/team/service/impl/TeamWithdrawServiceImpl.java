package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.OrderStatusEnum;
import com.norm.timemall.app.base.enums.WithdrawTagEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.pay.domain.dto.WithDrawDTO;
import com.norm.timemall.app.studio.service.OrderFlowService;
import com.norm.timemall.app.team.domain.pojo.WithdrawToALiPayBO;
import com.norm.timemall.app.team.mapper.TeamAccountMapper;
import com.norm.timemall.app.team.mapper.TeamBrandAlipayMapper;
import com.norm.timemall.app.team.mapper.TeamTransactionsMapper;
import com.norm.timemall.app.team.mapper.TeamWithdrawRecordMapper;
import com.norm.timemall.app.team.service.TeamWithdrawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TeamWithdrawServiceImpl implements TeamWithdrawService {
    @Autowired
    private TeamBrandAlipayMapper teamBrandAlipayMapper;
    @Autowired
    private TeamAccountMapper teamAccountMapper;

    @Autowired
    private TeamWithdrawRecordMapper teamWithdrawRecordMapper;
    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;
    @Autowired
    private AccountService accountService;
    @Override
    public WithdrawToALiPayBO toAliPay(WithDrawDTO dto) {
        log.info("群巅-提现到支付宝"+ JSON.toJSONString(dto));
        String brandId = accountService.
                findBrandInfoByUserId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .getId();

        // 查询账户信息 提现金额验证
        FinAccount account = teamAccountMapper.selectOneByFid(brandId, FidTypeEnum.BRAND.getMark());
        if(account == null || dto.getAmount().compareTo(account.getDrawable())>0){
            throw  new ErrorCodeException(CodeEnum.NO_SUFFICIENT_FUNDS);
        }
        BrandAlipay brandAlipay = teamBrandAlipayMapper.selectById(dto.getToAccountId());
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
        bo.setAmount(dto.getAmount());
        return bo;
    }

    @Override
    public void toAliPaySuccess(String orderNo, AlipayFundTransToaccountTransferResponse response) {
        String brandId = accountService.
                findBrandInfoByUserId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .getId();
        // update tag and msg
        WithdrawRecord record = teamWithdrawRecordMapper.selectById(orderNo);
        record.setTag(WithdrawTagEnum.SUCCESS.getMark())
                .setTagDesc(WithdrawTagEnum.SUCCESS.getDesc())
                .setMsg(JSON.toJSONString(response))
                .setModifiedAt(new Date());
        teamWithdrawRecordMapper.updateById(record);
        // account option
        teamAccountMapper.updateMinusAccountByFid(record.getAmount(),brandId,FidTypeEnum.BRAND.getMark());

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
