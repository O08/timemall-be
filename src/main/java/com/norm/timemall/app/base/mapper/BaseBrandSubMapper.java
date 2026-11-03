package com.norm.timemall.app.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.BrandSub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (sms_message)数据Mapper
 *
 * @author kancy
 * @since 2025-03-20 16:34:09
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BaseBrandSubMapper extends BaseMapper<BrandSub> {
  @Select("select * from brand_sub where brand_id=#{brandId}")
  BrandSub selectByBrandId(@Param("brandId") String brandId);
}
