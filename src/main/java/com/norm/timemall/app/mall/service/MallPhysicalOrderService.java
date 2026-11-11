package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.dto.MallOrderPhysicalProductDTO;
import com.norm.timemall.app.mall.domain.ro.MallPhysicalOrderDetailRO;
import org.springframework.stereotype.Service;

@Service
public interface MallPhysicalOrderService {



    /**
     * 实物商品下单
     */
    String newOrder(MallOrderPhysicalProductDTO dto);

    void executePayment(String orderId);

    /**
     * 查询实物订单详情（主订单 + 商品明细）
     */
    MallPhysicalOrderDetailRO findOrderDetail(String orderId);
}
