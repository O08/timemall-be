package com.norm.timemall.app.scheduled.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.PpcBillTagEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.mo.PpcBill;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.scheduled.mapper.TaskPpcBillMapper;
import com.norm.timemall.app.scheduled.service.TaskPpcBillScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class TaskPpcBillScheduleServiceImpl implements TaskPpcBillScheduleService {

    @Autowired
    private TaskPpcBillMapper taskPpcBillMapper;

    @Autowired
    private DefaultPayService defaultPayService;

    @Override
    public void generatePpcBill(String batch) {

        taskPpcBillMapper.callGeneratePpcBillProc(batch);

    }

    @Override
    public void autoPayPpcBill() {
        // 查询可用的ppc 账户采购金额
        FinAccount balance= taskPpcBillMapper.selectBalance();
        // 查询本批次需要支付的金额
        BigDecimal totalBillAmount= taskPpcBillMapper.selectBillTotalAmount();
        if(balance.getDrawable().compareTo(totalBillAmount)<0){
            return;
        }

        // 最大支付笔数为10000
        LambdaQueryWrapper<PpcBill> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(PpcBill::getTag, PpcBillTagEnum.REMIT.getMark());

        List<PpcBill> ppcBills = taskPpcBillMapper.selectList(wrapper);
        ppcBills.forEach(e->{

            if(!e.getSupplierBrandId().equals(balance.getFid())){

                TransferBO bo = generateTransferBO(e.getAmount(), e.getId(), balance.getFid(), e.getSupplierBrandId());
                String transNo=defaultPayService.transfer(new Gson().toJson(bo));
                // update bill and visit as paid
                taskPpcBillMapper.updateBillAndVisitInfo(e.getBatch(),e.getSupplierBrandId());

            }

        });

    }

    private TransferBO generateTransferBO(BigDecimal amount, String outNo,String payerAccount,String payeeAccount){

        TransferBO bo = new TransferBO();
        bo.setAmount(amount)
                .setOutNo(outNo)
                .setPayeeType(FidTypeEnum.BRAND.getMark())
                .setPayeeAccount(payeeAccount)
                .setPayerAccount(payerAccount)
                .setPayerType(FidTypeEnum.BRAND.getMark())
                .setTransType(TransTypeEnum.PPC_BILL.getMark());
        return  bo;

    }
}
