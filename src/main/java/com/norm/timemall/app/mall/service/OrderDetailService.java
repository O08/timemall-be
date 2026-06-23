package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.base.mo.OrderDetails;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.mall.domain.dto.OrderDTO;
import org.springframework.stereotype.Service;

@Service
public interface OrderDetailService {
    String newOrder(CustomizeUser userDetails, String cellId, OrderDTO orderDTO);

    OrderDetails findOrder(String orderId);
}
