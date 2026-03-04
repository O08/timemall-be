package com.norm.timemall.app.pod.handler;

import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.BillCategoiesEnum;
import com.norm.timemall.app.base.enums.BillMarkEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Bill;
import com.norm.timemall.app.base.mo.Proposal;
import com.norm.timemall.app.base.pojo.ProposalServiceItem;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.pod.mapper.PodBillMapper;
import com.norm.timemall.app.pod.mapper.PodProposalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Component
public class PodPayProposalBillHandler {
    @Autowired
    private PodBillMapper podBillMapper;

    @Autowired
    private PodProposalMapper podProposalMapper;

    @Autowired
    private DefaultPayService defaultPayService;

    public void doPayBill(Bill bill){
        Proposal proposal = podProposalMapper.selectById(bill.getOrderId());
        if(proposal==null){
            throw new QuickMessageException("未找到相关提案");
        }
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isBuyer=currentBrandId.equals(proposal.getBuyerBrandId());
        if(!isBuyer){
            throw new QuickMessageException("提案暂不支持代付");
        }
        BigDecimal netIncome=bill.getAmount();

        // pay
        if(netIncome.compareTo(BigDecimal.ZERO)>0){
            TransferBO bo = defaultPayService.generateTransferBO(TransTypeEnum.PROPOSAL_BILL_PAY.getMark(),
                    FidTypeEnum.BRAND.getMark(), proposal.getSellerBrandId(), FidTypeEnum.BRAND.getMark(), proposal.getBuyerBrandId(),netIncome, proposal.getId());
            defaultPayService.transfer(new Gson().toJson(bo));
        }
        // update bill mark as paid
        podBillMapper.updateBillInfoById(netIncome,BigDecimal.ZERO,BigDecimal.ZERO,bill.getId(), BillMarkEnum.PAID.getMark());

        // update proposal serviceProgress
        proposal.setServiceProgress(bill.getStageNo());
        proposal.setModifiedAt(new Date());
        podProposalMapper.updateById(proposal);

        // 支付完成后，生成下一条账单
        generateNextBill(proposal, bill);

    }
    private void generateNextBill(Proposal proposal,Bill prevBill){
        int index = Integer.parseInt(prevBill.getStageNo()) +1;
        Gson gson =new Gson();
        ProposalServiceItem[] services = gson.fromJson(proposal.getServices().toString(), ProposalServiceItem[].class);
        if(index<services.length){
            ProposalServiceItem service = services[index];
            BigDecimal amount = service.getQuantity().multiply(service.getPrice())
                    .setScale(2, RoundingMode.HALF_UP);
            Bill nextBill =new Bill();
            nextBill.setId(IdUtil.simpleUUID())
                    .setOrderId(proposal.getId()) // proposal id
                    .setStage(service.getServiceName())
                    .setStageNo(index+"")
                    .setAmount(amount)
                    .setPayeeFid(proposal.getSellerBrandId())
                    .setPayeeFidType(FidTypeEnum.BRAND.getMark())
                    .setPayerFid(proposal.getBuyerBrandId())
                    .setPayerFidType(FidTypeEnum.BRAND.getMark())
                    .setRemark(proposal.getProjectName())
                    .setCategories(BillCategoiesEnum.PROPOSAL.getMark())
                    .setMark(BillMarkEnum.CREATED.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());

            podBillMapper.insert(nextBill);
        }
    }
}
