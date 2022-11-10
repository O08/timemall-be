package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.mall.domain.dto.OrderDTO;
import org.springframework.stereotype.Service;

@Service
public interface OrderDetailService {
    void newOrder(CustomizeUser userDetails, String cellId, OrderDTO orderDTO);
}
