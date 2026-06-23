package com.norm.timemall.app.affiliate.mapper;

import com.norm.timemall.app.affiliate.domain.ro.HotOutreachRO;
import com.norm.timemall.app.base.mo.AffiliateHotOutreach;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * 渠道榜(affiliate_hot_outreach)数据Mapper
 *
 * @author kancy
 * @since 2024-06-05 10:46:51
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface AffiliateHotOutreachMapper extends BaseMapper<AffiliateHotOutreach> {

    ArrayList<HotOutreachRO> selectOutReachByBrandIdAndTimeSpan(@Param("brandId") String brandId, @Param("timespan") String timespan);
}
