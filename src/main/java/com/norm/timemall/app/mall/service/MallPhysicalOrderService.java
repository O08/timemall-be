package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.dto.MallOrderPhysicalProductDTO;
import org.springframework.stereotype.Service;

@Service
public interface MallPhysicalOrderService {



    /**
     * 实物商品下单
     */
    String newOrder(MallOrderPhysicalProductDTO dto);

    void executePayment(String orderId);
}
