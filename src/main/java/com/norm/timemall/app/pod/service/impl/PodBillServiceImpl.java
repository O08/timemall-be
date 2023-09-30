package com.norm.timemall.app.pod.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.BillMarkEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Bill;
import com.norm.timemall.app.base.mo.Millstone;
import com.norm.timemall.app.base.mo.OrderDetails;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.pod.domain.dto.PodBillPageDTO;
import com.norm.timemall.app.pod.domain.pojo.PodMillStoneNode;
import com.norm.timemall.app.pod.domain.pojo.PodWorkFlowNode;
import com.norm.timemall.app.pod.domain.ro.PodBillsRO;
import com.norm.timemall.app.pod.mapper.PodBillMapper;
import com.norm.timemall.app.pod.mapper.PodMillstoneMapper;
import com.norm.timemall.app.pod.mapper.PodOrderDetailsMapper;
import com.norm.timemall.app.pod.service.PodBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service
public class PodBillServiceImpl implements PodBillService {
    @Autowired
    private PodBillMapper podBillMapper;
    @Autowired
    private PodOrderDetailsMapper podOrderDetailsMapper;

    @Autowired
    private PodMillstoneMapper podMillstoneMapper;

    @Autowired
    private DefaultPayService defaultPayService;

    @Override
    public Bill findBillByIdAndCustomer(String billId, String customerId) {
        Bill bill =  podBillMapper.selectByIdAndCustomer( billId,  customerId);
        return bill;
    }



    @Override
    public IPage<PodBillsRO> findBills(PodBillPageDTO pageDTO, CustomizeUser user) {
        IPage<PodBillsRO> page = new Page<>();
        page.setCurrent(pageDTO.getCurrent());
        page.setSize(pageDTO.getSize());
        IPage<PodBillsRO>  bills = podBillMapper.selectBillPageByUserId(page, pageDTO.getCode(),user.getUserId());
        return bills;
    }

    @Override
    public void pay(String billId) {
        doPayMillstoneBill(billId);
    }

    public void doPayMillstoneBill(String billId){

        Bill bill = podBillMapper.selectById(billId);
        if(bill==null || !BillMarkEnum.PENDING.getMark().equals(bill.getMark())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        OrderDetails orderDetails = podOrderDetailsMapper.selectById(bill.getOrderId());
        if(!SecurityUserHelper.getCurrentPrincipal().getUserId().equals(orderDetails.getConsumerId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // pay
        TransferBO bo = generateTransferBO(bill.getAmount(),orderDetails.getBrandId(),billId);
        defaultPayService.transfer(new Gson().toJson(bo));
        // update bill mark as paid
        podBillMapper.updateBillMarkById(billId,BillMarkEnum.PAID.getMark());

        // 支付完成后，生成下一条账单
        generateNextBill(billId);


    }

    private TransferBO generateTransferBO(BigDecimal amount, String payeeAccount,String outNo){
        TransferBO bo = new TransferBO();
        bo.setAmount(amount)
                .setOutNo(outNo)
                .setPayeeType(FidTypeEnum.BRAND.getMark())
                .setPayeeAccount(payeeAccount)
                .setPayerAccount(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                .setPayerType(FidTypeEnum.BRAND.getMark())
                .setTransType(TransTypeEnum.MILLSTONE_BILL_PAY.getMark());
        return  bo;
    }

    private void generateNextBill(String billId){
        // find bill
        Bill bill = findBillByIdAndCustomer(billId, SecurityUserHelper.getCurrentPrincipal().getUserId());
        LambdaQueryWrapper<Millstone> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Millstone::getOrderId,bill.getOrderId());
        Millstone millstone = podMillstoneMapper.selectOne(wrapper);
        Gson gson = new Gson();
        if(millstone.getStageList() == null){
            return;
        }

        int index = Integer.parseInt(bill.getStageNo()) -1;
        if(index >= 0){
            PodWorkFlowNode workflow = gson.fromJson(millstone.getStageList().toString(), PodWorkFlowNode.class);
            PodMillStoneNode[] millstones = workflow.getMillstones();
            PodMillStoneNode millStoneNode = millstones[index];
            OrderDetails orderDetails = podOrderDetailsMapper.selectById(bill.getOrderId());
            BigDecimal amount = orderDetails.getTotal().multiply(BigDecimal.valueOf(millStoneNode.getPayRate() / 100d))
                    .setScale(2, RoundingMode.HALF_UP);

            Bill newBill =new Bill();
            newBill.setId(IdUtil.simpleUUID())
                    .setOrderId(orderDetails.getId()) // order id 在该版本与workId 相同--2022-12-16
                    .setStage( millStoneNode.getTitle())
                    .setStageNo("" + index)
                    .setAmount(amount)
                    .setMark(BillMarkEnum.CREATED.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());

            podBillMapper.insert(newBill);

        }




    }
}
