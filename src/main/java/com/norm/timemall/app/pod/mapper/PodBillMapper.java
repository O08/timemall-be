package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Bill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.pod.domain.ro.PodBillsRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * (bill)数据Mapper
 *
 * @author kancy
 * @since 2022-10-27 11:26:23
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PodBillMapper extends BaseMapper<Bill> {
    @Select(value = "select b.id,b.stage,b.stage_no,b.amount,b.voucher,b.mark,b.order_id,b.create_at,b.modified_at from bill b,order_details d where b.order_id = d.id and b.id= #{id} and d.consumer_id = #{user_id}")
    Bill selectByIdAndCustomer(@Param("id") String billId, @Param("user_id") String userId);

    @Update(value = "update bill set mark = #{code},net_income=#{netIncome},commission=#{commission} where id = #{id}")
    void updateBillInfoById(@Param("netIncome") BigDecimal netIncome, @Param("commission") BigDecimal commission, @Param("id")  String billId, @Param("code") String code);

    IPage<PodBillsRO> selectBillPageByUserId(IPage<PodBillsRO> page,  @Param("mark") String  code, @Param("user_id") String customerId);

}
