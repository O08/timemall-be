package com.norm.timemall.app.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.norm.timemall.app.base.mo.OrderFlow;

public interface OrderFlowService extends IService<OrderFlow> {
    /**
     * insert order flow
     * @param id ProprietaryTradingOrder id  or other id
     * @param stage ProprietaryTradingOrder status
     * @return insert return
     */
    int insertOrderFlow(String id ,String stage);

    /**
     * delete order flow
     * @param id ProprietaryTradingOrder id or other id
     * @param stage delete return
     * @return
     */
    int deleteOrderFlow(String id,String stage);

    int insertAutoFlow(String id ,String stage);
    int deleteAutoFlow(String id,String stage);
    boolean existsAutoFlow(String id,String stage);
    OrderFlow findAutoFlow(String id,String stage);

    /**
     * 尝试获取分布式锁
     * @return true: 抢锁成功，允许执行业务; false: 锁已被占用且未超时，抢锁失败
     */
    boolean tryLock(String id, String stage, int timeoutMinutes);

    /**
     * 释放分布式锁
     */
    void unlock(String id, String stage);

}
