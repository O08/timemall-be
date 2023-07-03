package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CommercialPaper;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.mo.MpsFund;
import com.norm.timemall.app.base.mo.Transactions;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsPaperDeliverTagDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsFund;
import com.norm.timemall.app.studio.mapper.StudioCommercialPaperMapper;
import com.norm.timemall.app.studio.mapper.StudioFinanceAccountMapper;
import com.norm.timemall.app.studio.mapper.StudioMpsFundMapper;
import com.norm.timemall.app.studio.mapper.StudioTransactionsMapper;
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
    private StudioTransactionsMapper studioTransactionsMapper;
    @Autowired
    private StudioCommercialPaperMapper studioCommercialPaperMapper;


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
        FinAccount brandFinAccount = studioFinanceAccountMapper.selectOneByFidForUpdate(brandId, FidTypeEnum.BRAND.getMark());

        // query brand mps fund fin account
        FinAccount mpsFundFinAccount = studioFinanceAccountMapper.selectOneByFidForUpdate(mpsFundId,FidTypeEnum.MPS_FUND.getMark());
        // query brand mps fund account
        MpsFund mpsFund =studioMpsFundMapper.selectById(mpsFundId);
        // validate
        if(brandFinAccount == null || mpsFundFinAccount == null || mpsFund == null ||
                brandFinAccount.getDrawable().compareTo(amount)<0){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // insert trans
        String transNo = IdUtil.simpleUUID();
        Transactions creditTrans = new Transactions();
        creditTrans.setId(IdUtil.simpleUUID())
                .setFid(mpsFundId)
                .setFidType(FidTypeEnum.MPS_FUND.getMark())
                .setTransNo(transNo)
                .setTransType(TransTypeEnum.TOPUP_MPS_FUND.getMark())
                .setTransTypeDesc(TransTypeEnum.TOPUP_MPS_FUND.getDesc())
                .setAmount(amount)
                .setDirection(TransDirectionEnum.CREDIT.getMark())
                .setRemark(TransTypeEnum.TOPUP_MPS_FUND.getDesc())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        Transactions debitTrans = new Transactions();
        debitTrans.setId(IdUtil.simpleUUID())
                .setFid(brandId)
                .setFidType(FidTypeEnum.BRAND.getMark())
                .setTransNo(transNo)
                .setTransType(TransTypeEnum.TOPUP_MPS_FUND.getMark())
                .setTransTypeDesc(TransTypeEnum.TOPUP_MPS_FUND.getDesc())
                .setAmount(amount)
                .setDirection(TransDirectionEnum.DEBIT.getMark())
                .setRemark(TransTypeEnum.TOPUP_MPS_FUND.getDesc())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioTransactionsMapper.insert(creditTrans);
        studioTransactionsMapper.insert(debitTrans);

        // action option
        BigDecimal brandBalance = brandFinAccount.getDrawable().subtract(amount);
        brandFinAccount.setDrawable(brandBalance);
        studioFinanceAccountMapper.updateById(brandFinAccount);

        BigDecimal mpsFundBalance = mpsFundFinAccount.getDrawable().add(amount);
        mpsFundFinAccount.setDrawable(mpsFundBalance);
        studioFinanceAccountMapper.updateById(mpsFundFinAccount);

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
        CommercialPaper paper = studioCommercialPaperMapper.selectPaperByDeliverId(dto.getDeliverId(), CommercialPaperDeliverTagEnum.CREATED.getMark());
        if(paper==null || CommercialPaperTagEnum.END.getMark().equals(paper.getTag())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // query mps fund and mps fund fin account
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // query mps_fund account
        LambdaQueryWrapper<MpsFund> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MpsFund::getFounder,brandId);

        MpsFund qFund = studioMpsFundMapper.selectOne(wrapper);

        FinAccount mpsFundFinAccount = studioFinanceAccountMapper.selectOneByFidForUpdate(qFund.getId(), FidTypeEnum.MPS_FUND.getMark());
        // query mps supplier brand fin account
        FinAccount supplierFinAccount = studioFinanceAccountMapper.selectOneByFidForUpdate(paper.getSupplier(), FidTypeEnum.BRAND.getMark());

        // validate
        if(supplierFinAccount == null || mpsFundFinAccount == null ||
                mpsFundFinAccount.getDrawable().compareTo(paper.getBonus())<0){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // insert trans
        String transNo = IdUtil.simpleUUID();
        Transactions creditTrans = new Transactions();
        creditTrans.setId(IdUtil.simpleUUID())
                .setFid(supplierFinAccount.getFid())
                .setFidType(FidTypeEnum.BRAND.getMark())
                .setTransNo(transNo)
                .setTransType(TransTypeEnum.MPS_FUND_TRANSFER.getMark())
                .setTransTypeDesc(TransTypeEnum.MPS_FUND_TRANSFER.getDesc())
                .setAmount(paper.getBonus())
                .setDirection(TransDirectionEnum.CREDIT.getMark())
                .setRemark(TransTypeEnum.MPS_FUND_TRANSFER.getDesc())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        Transactions debitTrans = new Transactions();
        debitTrans.setId(IdUtil.simpleUUID())
                .setFid(mpsFundFinAccount.getFid())
                .setFidType(FidTypeEnum.MPS_FUND.getMark())
                .setTransNo(transNo)
                .setTransType(TransTypeEnum.MPS_FUND_TRANSFER.getMark())
                .setTransTypeDesc(TransTypeEnum.MPS_FUND_TRANSFER.getDesc())
                .setAmount(paper.getBonus())
                .setDirection(TransDirectionEnum.DEBIT.getMark())
                .setRemark(TransTypeEnum.MPS_FUND_TRANSFER.getDesc())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioTransactionsMapper.insert(creditTrans);
        studioTransactionsMapper.insert(debitTrans);

        // action option
        BigDecimal mpsFundBalance = mpsFundFinAccount.getDrawable().subtract(paper.getBonus());
        mpsFundFinAccount.setDrawable(mpsFundBalance);
        studioFinanceAccountMapper.updateById(mpsFundFinAccount);

        BigDecimal supplierBalance = supplierFinAccount.getDrawable().add(paper.getBonus());
        supplierFinAccount.setDrawable(supplierBalance);
        studioFinanceAccountMapper.updateById(supplierFinAccount);

        // update mps_fund
        qFund.setBalance(mpsFundBalance);
        studioMpsFundMapper.updateById(qFund);


    }
}
