package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.Subscription;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubscriptionPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubscriptionPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * (subscription)数据Mapper
 *
 * @author kancy
 * @since 2025-07-25 14:26:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioSubscriptionMapper extends BaseMapper<Subscription> {

    IPage<StudioGetSubscriptionPageRO> selectPageByQ(Page<StudioGetSubscriptionPageRO> page, @Param("dto") StudioGetSubscriptionPageDTO dto, @Param("seller_brand_id") String sellerBrandId);
@Update("update subscription set status=#{status},modified_at=#{modify_at} where id=#{id}")
    void updateStatusAndModifyAtById(@Param("id") String id, @Param("status") String status,  @Param("modify_at") Date date);

}
