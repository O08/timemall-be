package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.OrderDetails;
import com.norm.timemall.app.mall.domain.pojo.InsertOrderParameter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * (order_details)数据Mapper
 *
 * @author kancy
 * @since 2022-10-26 15:48:45
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOrderDetailsMapper extends BaseMapper<OrderDetails> {
    void insertNewOrder(@Param("pt")InsertOrderParameter parameter);
}
