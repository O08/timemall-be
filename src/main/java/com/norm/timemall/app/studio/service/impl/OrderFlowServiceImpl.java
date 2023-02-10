package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.OrderFlow;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.studio.mapper.OrderFlowMapper;
import com.norm.timemall.app.studio.service.OrderFlowService;
import org.springframework.stereotype.Service;

@Service
public class OrderFlowServiceImpl extends ServiceImpl<OrderFlowMapper, OrderFlow> implements OrderFlowService {


    @Override
    public int insertOrderFlow(String id, String stage) {
        // CustomizeUser
        CustomizeUser user = SecurityUserHelper.getCurrentPrincipal();
        // insert
        OrderFlow orderFlow = new OrderFlow();
        orderFlow.setOrderId(id)
                .setStage(stage)
                .setUserId(user.getUserId());
        int cnt = this.baseMapper.insert(orderFlow);
        return cnt;
    }




    @Override
    public int deleteOrderFlow(String id, String stage) {
        // get user id
        CustomizeUser user = SecurityUserHelper.getCurrentPrincipal();
        LambdaQueryWrapper<OrderFlow> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(OrderFlow::getOrderId,id)
                .eq(OrderFlow::getStage,stage)
                .eq(OrderFlow::getUserId,user.getUserId());
        return this.baseMapper.delete(wrapper);
    }
}
