package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OrderDetails;
import com.norm.timemall.app.pod.domain.ro.PodTransRO;
import com.norm.timemall.app.pod.domain.ro.PodWorkflowRO;
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
public interface PodOrderDetailsMapper extends BaseMapper<OrderDetails> {

   @Select("select d.sbu,d.quantity, d.cell_title service, d.brand_name supplier,d.total fee,d.create_at added,d.brand_id,b.customer_id as supplierUserId from order_details d, brand b where b.id=d.brand_id and d.consumer_id = #{user_id} order by d.create_at desc")
    IPage<PodTransRO> selectTransPageByUserId(IPage<PodTransRO> page, @Param("user_id") String username);
    @Select("select o.sbu,o.quantity,o.cell_title service, o.brand_name supplier,o.total fee,o.create_at added,o.id from order_details o,millstone m where   o.id = m.order_id and o.consumer_id = #{user_id} and m.mark= #{mark} order by o.create_at desc")
    IPage<PodWorkflowRO> selectWorkflowByUserId(IPage<PodWorkflowRO> page, @Param("mark") String code,@Param("user_id") String userId);
}
