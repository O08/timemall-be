package com.norm.timemall.app.pod.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.pod.domain.dto.PodBillPageDTO;
import com.norm.timemall.app.pod.domain.pojo.PodMillStoneNode;
import com.norm.timemall.app.pod.domain.pojo.PodWorkFlowNode;
import com.norm.timemall.app.pod.domain.ro.FetchBillDetailRO;
import com.norm.timemall.app.pod.domain.ro.PodBillsRO;
import com.norm.timemall.app.pod.handler.PodPayProposalBillHandler;
import com.norm.timemall.app.pod.mapper.*;
import com.norm.timemall.app.pod.service.PodAffiliatePayService;
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
    @Autowired
    private PodAffiliatePayService podAffiliatePayService;

    @Autowired
    private PodAffiliateOrderMapper podAffiliateOrderMapper;

    @Autowired
    private PodCreditCouponMapper podCreditCouponMapper;

    @Autowired
    private PodOrderCouponMapper podOrderCouponMapper;

    @Autowired
    private PodPayProposalBillHandler podPayProposalBillHandler;

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
        IPage<PodBillsRO>  bills = podBillMapper.selectBillPageByPayerBrandId(page, pageDTO,user.getBrandId());
        return bills;
    }

    @Override
    public void pay(String billId) {
        Bill bill = podBillMapper.selectById(billId);
        if(bill==null){
            throw new QuickMessageException("未找到相关账单");
        }
        if(!BillMarkEnum.PENDING.getMark().equals(bill.getMark())){
            throw new QuickMessageException("账单状态验证失败");
        }
        if(BillCategoiesEnum.CELL.getMark().equals(bill.getCategories())){
            doPayMillstoneBill(bill);
        }
        if(BillCategoiesEnum.PROPOSAL.getMark().equals(bill.getCategories())){
            podPayProposalBillHandler.doPayBill(bill);
        }

    }

    @Override
    public FetchBillDetailRO findbillDetail(String id) {
        Bill bill = podBillMapper.selectById(id);
        if(bill==null){
            throw new QuickMessageException("未找到相关账单");
        }
        if(BillCategoiesEnum.CELL.getMark().equals(bill.getCategories())){
            return podBillMapper.selectBillDetailById(id,SecurityUserHelper.getCurrentPrincipal().getBrandId(),SecurityUserHelper.getCurrentPrincipal().getUserId());
        }
        // proposal  bill detail vo
        FetchBillDetailRO ro = new FetchBillDetailRO();
        ro.setAmount(bill.getAmount().toString());
        ro.setItem(bill.getStage());
        return ro;
    }

    public void doPayMillstoneBill(Bill bill){


        OrderDetails orderDetails = podOrderDetailsMapper.selectById(bill.getOrderId());
        if(!SecurityUserHelper.getCurrentPrincipal().getUserId().equals(orderDetails.getConsumerId())){
            throw new QuickMessageException("暂不支持代付");
        }
        // cal promotion discount
        BigDecimal promotionDeduction=calPromotionDeduction(orderDetails.getBrandId(),orderDetails.getId(),bill.getAmount());

       //cal commission and netIncome
        LambdaQueryWrapper<AffiliateOrder> affiliateOrderLambdaQueryWrapper= Wrappers.lambdaQuery();
        affiliateOrderLambdaQueryWrapper.eq(AffiliateOrder::getOrderId,orderDetails.getId())
                .eq(AffiliateOrder::getOrderType, AffiliateOrderTypeEnum.CELL.getMark());
        AffiliateOrder affiliateOrder = podAffiliateOrderMapper.selectOne(affiliateOrderLambdaQueryWrapper);
        BigDecimal commission=BigDecimal.ZERO;
        BigDecimal revenue=bill.getAmount().subtract(promotionDeduction);

        if(ObjectUtil.isNotNull(affiliateOrder) && revenue.compareTo(BigDecimal.ZERO)>0){
            commission=affiliateOrder.getRevshare().multiply(revenue).divide(new BigDecimal(100),2,RoundingMode.HALF_UP);
        }
        BigDecimal netIncome=revenue.subtract(commission);

        // pay
        if(netIncome.compareTo(BigDecimal.ZERO)>0){
            TransferBO bo = generateTransferBO(revenue,orderDetails.getBrandId(),bill.getId());
            defaultPayService.transfer(new Gson().toJson(bo));
        }

        // update bill mark as paid
        podBillMapper.updateBillInfoById(netIncome,commission,promotionDeduction,bill.getId(),BillMarkEnum.PAID.getMark());


        // 支付完成后，生成下一条账单
        generateNextBill(bill.getId(), bill.getStageNo());

        // pay affiliate if order belong to affiliate order
        if(ObjectUtil.isNotNull(affiliateOrder) && commission.compareTo(BigDecimal.ZERO)>0){
            podAffiliatePayService.cellBillRevshare(affiliateOrder.getInfluencer(), commission, bill.getId(), orderDetails.getBrandId());
        }

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

    private void generateNextBill(String billId,String doneStageNo){

        CustomizeUser payer =SecurityUserHelper.getCurrentPrincipal();
        // find bill
        Bill bill = findBillByIdAndCustomer(billId, payer.getUserId());
        LambdaQueryWrapper<Millstone> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Millstone::getOrderId,bill.getOrderId());
        Millstone millstone = podMillstoneMapper.selectOne(wrapper);
        millstone.setDoneStageNo(doneStageNo);
        // update millstone workflow doneStageNo
        podMillstoneMapper.update(millstone,wrapper);

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
                    .setPayeeFid(workflow.getServiceInfo().getBrandId())
                    .setPayeeFidType(FidTypeEnum.BRAND.getMark())
                    .setPayerFid(payer.getBrandId())
                    .setPayerFidType(FidTypeEnum.BRAND.getMark())
                    .setRemark(workflow.getServiceInfo().getTitle())
                    .setCategories(BillCategoiesEnum.CELL.getMark())
                    .setMark(BillMarkEnum.CREATED.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());

            podBillMapper.insert(newBill);

        }

    }
    private BigDecimal calPromotionDeduction(String supplierBrandId,String orderId,BigDecimal total){
        // query credit point
       String consumerBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<CreditCoupon> creditCouponLambdaQueryWrapper=Wrappers.lambdaQuery();
        creditCouponLambdaQueryWrapper.eq(CreditCoupon::getSupplierBrandId,supplierBrandId)
                .eq(CreditCoupon::getConsumerBrandId,consumerBrandId);

        CreditCoupon creditCoupon = podCreditCouponMapper.selectOne(creditCouponLambdaQueryWrapper);
        // query order discount info
        LambdaQueryWrapper<OrderCoupon> orderCouponLambdaQueryWrapper=Wrappers.lambdaQuery();
        orderCouponLambdaQueryWrapper.eq(OrderCoupon::getOrderId,orderId)
                .eq(OrderCoupon::getOrderType,AffiliateOrderTypeEnum.CELL.getMark());
        OrderCoupon orderCoupon = podOrderCouponMapper.selectOne(orderCouponLambdaQueryWrapper);
       BigDecimal promotionCreditPointDeductionDifference=BigDecimal.ZERO;
       if(  ObjectUtil.isNotNull(creditCoupon)
           && (creditCoupon.getCreditPoint().compareTo(total)>=0)
         ){
           promotionCreditPointDeductionDifference=total;

       }

        if(  ObjectUtil.isNotNull(creditCoupon)
                && (creditCoupon.getCreditPoint().compareTo(total)<0)
        ){
            promotionCreditPointDeductionDifference=creditCoupon.getCreditPoint();
        }
        BigDecimal earlyBirdDiscountDifference=BigDecimal.ZERO;
        BigDecimal repurchaseDiscountDifference=BigDecimal.ZERO;

        if(ObjectUtil.isNotNull(orderCoupon)){
            BigDecimal c= BigDecimal.valueOf (100- orderCoupon.getEarlyBirdDiscount());
            earlyBirdDiscountDifference= total.multiply(c).divide(BigDecimal.valueOf(100),20, RoundingMode.HALF_UP);
            BigDecimal d= BigDecimal.valueOf(100-orderCoupon.getRepurchaseDiscount());
            repurchaseDiscountDifference=total.multiply(d).divide(BigDecimal.valueOf(100),20, RoundingMode.HALF_UP);
        }
        BigDecimal totalPromotionDeduction=promotionCreditPointDeductionDifference
                .add(earlyBirdDiscountDifference).add(repurchaseDiscountDifference);

        // update credit coupon point
        if(promotionCreditPointDeductionDifference.compareTo(BigDecimal.ZERO)>0){
            BigDecimal balancePoint=creditCoupon.getCreditPoint().subtract(promotionCreditPointDeductionDifference);
            podCreditCouponMapper.updatePointBySupplierAndConsumer(balancePoint,supplierBrandId,SecurityUserHelper.getCurrentPrincipal().getBrandId());
        }

        BigDecimal promotionDeduction= BigDecimal.ZERO;

        if(total.compareTo(totalPromotionDeduction)>=0){
            promotionDeduction=totalPromotionDeduction;
        }
        if(total.compareTo(totalPromotionDeduction)<0){
            promotionDeduction=total;
        }

        return  promotionDeduction;
    }
}
