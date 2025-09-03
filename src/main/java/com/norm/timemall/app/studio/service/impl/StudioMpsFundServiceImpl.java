package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CommercialPaper;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.mo.MpsFund;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsPaperDeliverTagDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsFund;
import com.norm.timemall.app.studio.mapper.StudioCommercialPaperMapper;
import com.norm.timemall.app.studio.mapper.StudioFinanceAccountMapper;
import com.norm.timemall.app.studio.mapper.StudioMpsFundMapper;
import com.norm.timemall.app.studio.service.StudioMpsFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class StudioMpsFundServiceImpl implements StudioMpsFundService {
    @Autowired
    private StudioMpsFundMapper studioMpsFundMapper;
    @Autowired
    private StudioFinanceAccountMapper studioFinanceAccountMapper;

    @Autowired
    private StudioCommercialPaperMapper studioCommercialPaperMapper;

    @Autowired
    private DefaultPayService defaultPayService;


    @Override
    public StudioFetchMpsFund getMpsFundForBrand() {
        // query brand id
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        StudioFetchMpsFund  fund= studioMpsFundMapper.selectMpsFundByBrandId(brandId, FidTypeEnum.BRAND.getMark());
        return fund;
    }
    @Transactional
    @Override
    public void topUpToMpsFund(BigDecimal amount,String mpsFundId) {

        // query brand finance account
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // query brand mps fund account
        MpsFund mpsFund =studioMpsFundMapper.selectById(mpsFundId);

        TransferBO bo = defaultPayService.generateTransferBO(TransTypeEnum.TOPUP_MPS_FUND.getMark(),
                FidTypeEnum.MPS_FUND.getMark(),mpsFundId,FidTypeEnum.BRAND.getMark(),brandId,amount,mpsFundId);

        defaultPayService.transfer(new Gson().toJson(bo));

        BigDecimal mpsFundBalance = mpsFund.getBalance().add(amount);

        // update mps_fund
        mpsFund.setBalance(mpsFundBalance);
        studioMpsFundMapper.updateById(mpsFund);


    }

    @Override
    public void applyMpsFundAccount() {
        // query brand finance account
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // query mps_fund account
        LambdaQueryWrapper<MpsFund> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MpsFund::getFounder,brandId);

        MpsFund qFund = studioMpsFundMapper.selectOne(wrapper);
        if(ObjectUtil.isNotEmpty(qFund)){
            throw new ErrorCodeException(CodeEnum.MPS_FUND_ALREADY_EXIST);
        }

        // create mps_fund account
        MpsFund mpsFund= new MpsFund();
        mpsFund.setId(IdUtil.simpleUUID())
                .setBalance(BigDecimal.ZERO)
                .setFounder(brandId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioMpsFundMapper.insert(mpsFund);
        // create finAccount
        FinAccount financeAccount = new FinAccount();
        financeAccount.setId(IdUtil.simpleUUID())
                        .setFid(mpsFund.getId())
                        .setFidType(FidTypeEnum.MPS_FUND.getMark())
                        .setDrawable(BigDecimal.ZERO)
                        .setAmount(BigDecimal.ZERO);

        studioFinanceAccountMapper.insert(financeAccount);
    }
    @Transactional
    @Override
    public void payMpsPaperFee(StudioPutMpsPaperDeliverTagDTO dto) {
        // query mps paper
        CommercialPaper paper = studioCommercialPaperMapper.selectPaperByDeliverId(dto.getDeliverId(), DeliverTagEnum.CREATED.getMark());
        if(paper==null || CommercialPaperTagEnum.END.getMark().equals(paper.getTag())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // query mps_fund account
        LambdaQueryWrapper<MpsFund> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MpsFund::getFounder,brandId);

        MpsFund qFund = studioMpsFundMapper.selectOne(wrapper);

        if(qFund==null){
            throw new QuickMessageException("未开通商单基金账户，前往商单助手开通后再继续。");
        }

        // pay
        TransferBO bo = defaultPayService.generateTransferBO(TransTypeEnum.MPS_FUND_TRANSFER.getMark(),
                FidTypeEnum.BRAND.getMark(), paper.getSupplier(), FidTypeEnum.MPS_FUND.getMark(), qFund.getId(), paper.getBonus(), paper.getId());

        defaultPayService.transfer(new Gson().toJson(bo));


        BigDecimal mpsFundBalance = qFund.getBalance().subtract(paper.getBonus());
        // update mps_fund
        qFund.setBalance(mpsFundBalance);
        studioMpsFundMapper.updateById(qFund);


    }
}
