package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.AffiliateOrderTypeEnum;
import com.norm.timemall.app.base.enums.OrderTypeEnum;
import com.norm.timemall.app.base.enums.WorkflowMarkEnum;
import com.norm.timemall.app.base.mo.Millstone;
import com.norm.timemall.app.base.mo.OrderDetails;
import com.norm.timemall.app.base.mo.Pricing;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.mall.domain.dto.OrderDTO;
import com.norm.timemall.app.mall.domain.pojo.InsertOrderParameter;
import com.norm.timemall.app.mall.mapper.MillstoneMapper;
import com.norm.timemall.app.mall.mapper.OrderDetailsMapper;
import com.norm.timemall.app.mall.mapper.PricingMapper;
import com.norm.timemall.app.mall.service.MallAffiliateOrderService;
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
    @Override
    public String newOrder(CustomizeUser userDetails, String cellId, OrderDTO orderDTO) {
        //
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
}
