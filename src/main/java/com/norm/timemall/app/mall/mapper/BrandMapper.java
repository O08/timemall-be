package com.norm.timemall.app.mall.mapper;

import com.norm.timemall.app.base.mo.Brand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.mall.domain.ro.BrandProfileRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (brand)数据Mapper
 *
 * @author kancy
 * @since 2022-10-26 15:23:17
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BrandMapper extends BaseMapper<Brand> {

    BrandProfileRO selectProileByBrandId(@Param("brandId") String brandId);

}
