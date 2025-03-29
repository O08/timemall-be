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
}
