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

   @Select("select cell_title service, brand_name supplier,total fee,create_at added,brand_id from order_details where consumer_id = #{user_id}")
    IPage<PodTransRO> selectTransPageByUserId(IPage<PodTransRO> page, @Param("user_id") String username);
    @Select("select o.cell_title service, o.brand_name supplier,o.total fee,o.create_at added,o.id from order_details o,millstone m where   o.id = m.order_id and o.consumer_id = #{user_id} and m.mark= #{mark}")
    IPage<PodWorkflowRO> selectWorkflowByUserId(IPage<PodWorkflowRO> page, @Param("mark") String code,@Param("user_id") String userId);
}
