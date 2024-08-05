package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.mall.domain.dto.MallFetchPromotionInfoDTO;
import com.norm.timemall.app.mall.domain.dto.OrderDTO;
import com.norm.timemall.app.mall.domain.pojo.InsertOrderParameter;
import com.norm.timemall.app.mall.domain.ro.MallFetchPromotionBenefitRO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPromotionInfoRO;
import com.norm.timemall.app.mall.mapper.*;
import com.norm.timemall.app.mall.service.MallAffiliateOrderService;
import com.norm.timemall.app.mall.service.MallBrandPromotionService;
import com.norm.timemall.app.mall.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailsMapper orderDetailsMapper;

    @Autowired
    private MillstoneMapper millstoneMapper;
    @Autowired
    private MallAffiliateOrderService mallAffiliateOrderService;
    @Autowired
    private PricingMapper pricingMapper;
    @Autowired
    private CellMapper cellMapper;

    @Autowired
    private MallBrandPromotionService mallBrandPromotionService;
    @Autowired
    private MallOrderCouponMapper mallOrderCouponMapper;

    @Autowired
    private MallCreditCouponMapper mallCreditCouponMapper;
    @Override
    public String newOrder(CustomizeUser userDetails, String cellId, OrderDTO orderDTO) {
        //
        Cell cell = cellMapper.selectById(cellId);
        if(cell==null || !CellMarkEnum.ONLINE.getMark().equals(cell.getMark()) ){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(SecurityUserHelper.getCurrentPrincipal().getBrandId().equals(cell.getBrandId())){
            throw new ErrorCodeException(CodeEnum.FALSE_SHOPPING);
        }
        // 增加新订单
        String orderId = IdUtil.simpleUUID();
        InsertOrderParameter parameter = new InsertOrderParameter()
                .setId(orderId)
                .setUserId(userDetails.getUserId())
                .setUsername(userDetails.getUsername())
                .setCellId(cellId)
                .setQuantity(orderDTO.getQuantity())
                .setSbu(orderDTO.getSbu())
                .setOrderType(OrderTypeEnum.MALL.getMark());
        orderDetailsMapper.insertNewOrder(parameter);

        // 增加该订单对应的空Workflow
        Millstone millstone = new Millstone();
        millstone.setOrderId(orderId)
                .setMark(WorkflowMarkEnum.IN_QUEUE.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        millstoneMapper.insert(millstone);

        // 优惠领取
        getDiscount(cell.getBrandId(),orderId,cell.getId());

        // 佣金单
        LambdaQueryWrapper<Pricing> pricingLambdaQueryWrapper= Wrappers.lambdaQuery();
        pricingLambdaQueryWrapper.eq(Pricing::getCellId,cellId)
                        .eq(Pricing::getSbu,orderDTO.getSbu());
        Pricing pricing = pricingMapper.selectOne(pricingLambdaQueryWrapper);
        BigDecimal price = pricing.getPrice().multiply(new BigDecimal(orderDTO.getQuantity()));
        mallAffiliateOrderService.newAffiliateOrder(cellId,orderId, AffiliateOrderTypeEnum.CELL.getMark(),price,orderDTO.getInfluencer(),orderDTO.getChn(),orderDTO.getMarket());

        return orderId;

    }

    @Override
    public OrderDetails findOrder(String orderId) {
        OrderDetails orderDetails = orderDetailsMapper.selectById(orderId);
        return  orderDetails;
    }
    private void getDiscount(String supplierBrandId,String orderId,String cellId){
        String canUseDiscount="1";
        MallFetchPromotionInfoDTO mallFetchPromotionInfoDTO= new MallFetchPromotionInfoDTO();
        mallFetchPromotionInfoDTO.setBrandId(supplierBrandId);
        String consumerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        MallFetchPromotionInfoRO promotionInfo = mallBrandPromotionService.findPromotionInfo(mallFetchPromotionInfoDTO);

        if(ObjectUtil.isNull(promotionInfo)){
            return ;
        }

        MallFetchPromotionBenefitRO promotionBenefit = mallBrandPromotionService.findPromotionBenefit(cellId,supplierBrandId);
        Integer earlyBirdDiscount=100;
        Integer repurchaseDiscount=100;

        if(canUseDiscount.equals(promotionBenefit.getCanUseEarlyBirdCoupon()) &&
                BrandPromotionTagEnum.OPEN.getMark().equals(promotionInfo.getEarlyBirdDiscountTag()) ){
            earlyBirdDiscount=Integer.parseInt(promotionInfo.getEarlyBirdDiscount());
        }
        if(canUseDiscount.equals(promotionBenefit.getCanUseRepurchaseCoupon()) &&
                BrandPromotionTagEnum.OPEN.getMark().equals(promotionInfo.getRepurchaseDiscountTag())){
            repurchaseDiscount=Integer.parseInt(promotionInfo.getRepurchaseDiscount());
        }
        if(earlyBirdDiscount<100 || repurchaseDiscount<100){

            OrderCoupon orderCoupon=new OrderCoupon();
            orderCoupon.setId(IdUtil.simpleUUID())
                    .setOrderId(orderId)
                    .setCellId(cellId)
                    .setConsumerBrandId(consumerBrandId)
                    .setOrderType(AffiliateOrderTypeEnum.CELL.getMark())
                    .setEarlyBirdDiscount(earlyBirdDiscount)
                    .setRepurchaseDiscount(repurchaseDiscount)
                    .setCreditPoint(BigDecimal.ZERO)
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            mallOrderCouponMapper.insert(orderCoupon);

        }
        String alreadyGetCreditPoint="1";
        if(!alreadyGetCreditPoint.equals(promotionBenefit.getAlreadyGetCreditPoint())){
            CreditCoupon creditCoupon=new CreditCoupon();
            creditCoupon.setId(IdUtil.simpleUUID())
                    .setCreditPoint(promotionBenefit.getCreditPoint())
                    .setSupplierBrandId(supplierBrandId)
                    .setConsumerBrandId(consumerBrandId)
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            mallCreditCouponMapper.insert(creditCoupon);
        }



    }
}
