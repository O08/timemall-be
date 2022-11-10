package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.mo.Millstone;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.mall.domain.dto.OrderDTO;
import com.norm.timemall.app.mall.domain.pojo.InsertOrderParameter;
import com.norm.timemall.app.mall.mapper.MillstoneMapper;
import com.norm.timemall.app.mall.mapper.OrderDetailsMapper;
import com.norm.timemall.app.mall.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailsMapper orderDetailsMapper;

    @Autowired
    private MillstoneMapper millstoneMapper;
    @Override
    public void newOrder(CustomizeUser userDetails, String cellId, OrderDTO orderDTO) {
        // 增加新订单
        String orderId = IdUtil.simpleUUID();
        InsertOrderParameter parameter = new InsertOrderParameter()
                .setId(orderId)
                .setUserId(userDetails.getUserId())
                .setCellId(cellId)
                .setQuantity(orderDTO.getQuantity())
                .setSbu(orderDTO.getSbu());
        orderDetailsMapper.insertNewOrder(parameter);

        // 增加该订单对应的空Workflow
        Millstone millstone = new Millstone();
        millstone.setOrderId(orderId);
        millstone.setCreateAt(new Date());
        millstone.setModifiedAt(new Date());
        millstoneMapper.insert(millstone);


    }
}
