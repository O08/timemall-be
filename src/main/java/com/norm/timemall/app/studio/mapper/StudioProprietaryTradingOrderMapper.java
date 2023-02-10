package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.ProprietaryTradingOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (proprietary_trading_order)数据Mapper
 *
 * @author kancy
 * @since 2023-02-04 16:06:43
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioProprietaryTradingOrderMapper extends BaseMapper<ProprietaryTradingOrder> {
    @Update(value = "update proprietary_trading_order set status = #{status} where id = #{tradingOrderId}")
    void updateTradingOrderStatus(@Param("tradingOrderId") String tradingOrderId,  @Param("status") String status);
}
