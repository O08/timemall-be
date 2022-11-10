package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.Pricing;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * (pricing)数据Mapper
 *
 * @author kancy
 * @since 2022-10-26 14:15:39
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioPricingMapper extends BaseMapper<Pricing> {

    int insertBatchSomeColumn(Collection<Pricing> pricingList);
}
