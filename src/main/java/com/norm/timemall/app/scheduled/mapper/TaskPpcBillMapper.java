package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.mo.PpcBill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * (ppc_bill)数据Mapper
 *
 * @author kancy
 * @since 2024-10-03 15:48:46
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskPpcBillMapper extends BaseMapper<PpcBill> {

    void callGeneratePpcBillProc(@Param("batch") String batch);
    @Select("select a.* from bluvarrier b inner join fin_account a on b.brand_id=a.fid and a.fid_type='1' where role_code='billing' ")
    FinAccount selectBalance();
    @Select("select sum(amount) total from ppc_bill where tag='1'")
    BigDecimal selectBillTotalAmount();


    void updateBillAndVisitInfo(@Param("batch") String batch, @Param("supplierBrandId") String supplierBrandId);

}
