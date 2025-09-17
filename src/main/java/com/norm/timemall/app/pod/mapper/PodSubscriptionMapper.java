package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.Subscription;
import com.norm.timemall.app.pod.domain.dto.PodGetSubscriptionPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodGetSubscriptionPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * (subscription)数据Mapper
 *
 * @author kancy
 * @since 2025-07-25 14:26:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PodSubscriptionMapper extends BaseMapper<Subscription> {

    IPage<PodGetSubscriptionPageRO> selectPageByQ(Page<PodGetSubscriptionPageRO> page, @Param("dto") PodGetSubscriptionPageDTO dto, @Param("buyer_brand_id") String buyerBrandId);
}
