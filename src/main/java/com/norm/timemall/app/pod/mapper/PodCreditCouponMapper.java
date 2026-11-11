package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.CreditCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * (credit_coupon)数据Mapper
 *
 * @author kancy
 * @since 2024-08-03 10:31:53
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PodCreditCouponMapper extends BaseMapper<CreditCoupon> {
@Update("update credit_coupon set credit_point=#{balancePoint},modified_at=now() where supplier_brand_id=#{supplierBrandId} and consumer_brand_id=#{consumerBrandId}")
    void updatePointBySupplierAndConsumer(@Param("balancePoint") BigDecimal balancePoint,
                                          @Param("supplierBrandId") String supplierBrandId,
                                          @Param("consumerBrandId") String consumerBrandId);
}
