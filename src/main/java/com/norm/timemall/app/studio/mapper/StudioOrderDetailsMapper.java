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

   @Select("select cell_title service, customer_name customer,total fee,create_at added,id from order_details where brand_id = #{brand_id} and customer_id = #{user_id}")
    IPage<StudioTransRO> selectTransPageByBrandId(IPage<StudioTransRO> page,
                                                  @Param("brand_id") String brandId,
                                                  @Param("user_id") String userId
   );
}
