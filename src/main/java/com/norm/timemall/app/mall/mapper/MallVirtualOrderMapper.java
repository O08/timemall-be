package com.norm.timemall.app.mall.mapper;

import com.norm.timemall.app.base.mo.VirtualOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (virtual_order)数据Mapper
 *
 * @author kancy
 * @since 2025-04-29 11:09:58
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MallVirtualOrderMapper extends BaseMapper<VirtualOrder> {

    @Update("update virtual_order set tag=#{tag},already_pay=#{alreadyPay} where id=#{id}")
    void updateTagAndPayById(@Param("alreadyPay") String alreadyPay,@Param("tag") int tag, @Param("id") String orderId);

}
