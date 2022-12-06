package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Bill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.pod.domain.ro.PodBillsRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * (bill)数据Mapper
 *
 * @author kancy
 * @since 2022-10-27 11:26:23
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PodBillMapper extends BaseMapper<Bill> {
    @Select(value = "select b.id,b.stage,b.amount,b.voucher,b.mark,b.order_id,b.create_at,b.modified_at from bill b,order_details d where b.order_id = d.id and b.id= #{id} and d.customer_id = #{customer_id}")
    Bill selectByIdAndCustomer(@Param("id") String billId, @Param("customer_id") String customerId);
    @Update(value = "update bill set voucher = #{uri},modified_at=now() where id = #{id}")
    void updateBillVoucherById(@Param("id") String billId, @Param("uri") String uri);

    @Update(value = "update bill set mark = #{code} where id = #{id}")
    void updateBillMarkById(@Param("id")  String billId, @Param("code") String code);

    IPage<PodBillsRO> selectBillPageByUserId(IPage<PodBillsRO> page,  @Param("mark") String  code, @Param("user_id") String customerId);

}
