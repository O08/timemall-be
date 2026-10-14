package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.dto.OrderVirtualProductDTO;
import org.springframework.stereotype.Service;

@Service
public interface MallVirtualProductOrderService {
    String newOrder(OrderVirtualProductDTO dto);

    void repayOrder(String orderId);

}
