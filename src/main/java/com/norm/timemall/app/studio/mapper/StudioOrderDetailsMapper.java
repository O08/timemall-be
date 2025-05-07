package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OrderDetails;
import com.norm.timemall.app.studio.domain.ro.StudioTransRO;
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
public interface StudioOrderDetailsMapper extends BaseMapper<OrderDetails> {

   @Select("select o.sbu,o.quantity,o.cell_title service, o.consumer_name customer,o.total fee,o.create_at added,o.id,o.consumer_id customerUserId from order_details o,brand b where o.brand_id = b.id and b.customer_id = #{user_id} order by o.create_at desc")
    IPage<StudioTransRO> selectTransPageByBrandId(IPage<StudioTransRO> page,
                                                  @Param("user_id") String userId
   );
    @Select("select o.sbu,o.quantity, o.cell_title service, o.consumer_name customer,o.total fee,o.create_at added,o.id from order_details o,brand b,millstone m where o.brand_id = b.id and o.id = m.order_id and b.customer_id = #{user_id} and m.mark= #{code} order by o.create_at desc")
    IPage<StudioTransRO> selectWorkflowPageByBrandId(IPage<StudioTransRO> page,  @Param("user_id") String userId,@Param("code") String code);
}
