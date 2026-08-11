package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.SubsBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubsBillingPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsBillingPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * (subs_bill)数据Mapper
 *
 * @author kancy
 * @since 2025-07-25 14:26:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioSubsBillMapper extends BaseMapper<SubsBill> {

    IPage<StudioGetSubsBillingPageRO> selectPageByQ(Page<StudioGetSubsBillingPageRO> page, @Param("dto") StudioGetSubsBillingPageDTO dto, @Param("seller_brand_id") String sellerBrandId);
@Update("update subs_bill set status=#{status},modified_at=#{modify_at} where id=#{id}")
    void updateStatusAndModifyAtById(@Param("id") String id, @Param("status") String status,  @Param("modify_at") Date date);
    @Update("update subs_bill set trade_no=#{trade_no}, status=#{status},buyer_pay_at=#{modify_at}, modified_at=#{modify_at},where_store_money=#{where_store_money} where id=#{id}")
    void updateStatusAndModifyAtOnSuccessById(@Param("trade_no") String tradeNo ,@Param("id") String id,@Param("where_store_money") String whereStoreMoney, @Param("status") String status,@Param("modify_at") Date date);
}
