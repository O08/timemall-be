package com.norm.timemall.app.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.OrderFlow;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.mapper.OrderFlowMapper;
import com.norm.timemall.app.base.service.OrderFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
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

    @Override
    public int insertAutoFlow(String id, String stage) {

        // insert
        OrderFlow orderFlow = new OrderFlow();
        orderFlow.setOrderId(id)
                .setStage(stage)
                .setCreateAt(new Date())
                .setUserId(OperatorConfig.sysMidFinAccount);

        return this.baseMapper.insert(orderFlow);

    }

    @Override
    public int deleteAutoFlow(String id, String stage) {
        LambdaQueryWrapper<OrderFlow> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(OrderFlow::getOrderId,id)
                .eq(OrderFlow::getStage,stage)
                .eq(OrderFlow::getUserId,OperatorConfig.sysMidFinAccount);
        return this.baseMapper.delete(wrapper);
    }

    @Override
    public boolean existsAutoFlow(String id, String stage) {
        LambdaQueryWrapper<OrderFlow> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(OrderFlow::getOrderId,id)
                .eq(OrderFlow::getStage,stage)
                .eq(OrderFlow::getUserId,OperatorConfig.sysMidFinAccount);
        return this.baseMapper.exists(wrapper);
    }

    @Override
    public OrderFlow findAutoFlow(String id, String stage) {

        LambdaQueryWrapper<OrderFlow> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(OrderFlow::getOrderId,id)
                .eq(OrderFlow::getStage,stage)
                .eq(OrderFlow::getUserId,OperatorConfig.sysMidFinAccount);
        return this.baseMapper.selectOne(wrapper);

    }

    @Override
    public boolean tryLock(String id, String stage, int timeoutMinutes) {
        try {
             return this.baseMapper.insertLock(id, stage) > 0;
        } catch (DuplicateKeyException e) {
            // 如果更新成功（返回1），说明旧锁超时被我们成功踢掉；如果返回0，说明没超时，抢锁失败
            int updatedRows = this.baseMapper.updateTimeoutLock(id, stage, timeoutMinutes);
            return updatedRows > 0;
        } catch (Exception e) {
            log.error("分布式锁系统异常", e);
            return false;
        }
    }

    @Override
    public void unlock(String id, String stage) {
        this.lambdaUpdate()
                .eq(OrderFlow::getOrderId, id)
                .eq(OrderFlow::getStage, stage)
                .remove();
    }
}
