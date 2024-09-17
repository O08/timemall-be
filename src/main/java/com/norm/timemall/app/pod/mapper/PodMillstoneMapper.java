package com.norm.timemall.app.pod.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.Millstone;
import com.norm.timemall.app.pod.domain.pojo.PodWorkflowServiceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * (millstone)数据Mapper
 *
 * @author kancy
 * @since 2022-10-26 19:43:08
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PodMillstoneMapper extends BaseMapper<Millstone> {
    @Update(value = "update millstone set mark = #{code} where order_id=#{id} and stage_list is not null")
    void updateWorkflowByIdAndCode(@Param("id") String workflwoId, @Param("code") String code);

    @Select("select o.brand_id, o.cell_id, o.cell_title title,b.brand_name brand,b.avator avatar,o.consumer_id consumerUserId,b.customer_id supplierUserId from order_details o , brand b where o.brand_id = b.id and o.id=#{id} ")
    PodWorkflowServiceInfo selectWorkflowServiceInfoById(@Param("id") String workflwoId);

}
