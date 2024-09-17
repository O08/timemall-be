package com.norm.timemall.app.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.entity.Customer;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.mo.Cell;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (customer)数据Mapper
 *
 * @author kancy
 * @since 2022-10-24 19:38:28
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface DataPolicyMapper extends BaseMapper<Customer> {
@Select("select c.id,c.title,c.cover,c.intro_cover,c.content,c.brand_id,c.mark,c.create_at,c.modified_at from cell c ,brand d where c.id = #{id} and c.brand_id = d.id and d.customer_id=#{user_id}")
    Cell selectCellByCellIdAndCustomerId(@Param("id") String cellId, @Param("user_id") String userId);
@Select("select count(1) cnt from brand d where id = #{id} and d.customer_id=#{user_id}")
    Integer selectCountBrandByIdAndCustomerId(@Param("id") String brandId, @Param("user_id") String userId);

@Select("select count(1) cnt from order_details o where o.id = #{id} and o.consumer_id = #{user_id}")
    Integer selectCountWorkflowByIdAndCustomerId(@Param("id")  String workflwoId, @Param("user_id") String userId);
@Select("select count(1) cnt from bill b , order_details o where b.id = #{id} and b.order_id = o.id and o.consumer_id = #{user_id}")
    Integer selectCountBillIdByIdAndCustomerId(@Param("id")  String billId,@Param("user_id")  String userId);
@Select("select count(1) cnt from order_details where brand_id = #{brand_id} and consumer_id = #{user_id}")
    Integer selectCountOrderDetails(@Param("brand_id") String brandId, @Param("user_id") String userId);
    @Select("select count(1) cnt from order_details o, brand b where o.id= #{id} and o.brand_id = b.id and b.customer_id = #{user_id}")
    Integer selectCountWorkflowForBrandByIdAndCustomerId(@Param("id") String workflwoId, @Param("user_id") String userId);
    @Select("select count(1) cnt from bill b , order_details o, brand r where o.brand_id = r.id and b.id = #{id} and b.order_id = o.id and r.customer_id = #{user_id} and b.mark=#{mark}")
    Integer selectCountBillIdForBrandByIdAndCustomerId(@Param("id")  String billId,@Param("user_id")  String userId,@Param("mark") String mark);
}
