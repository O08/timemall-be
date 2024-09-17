package com.norm.timemall.app.mall.mapper;

import com.norm.timemall.app.base.mo.Pricing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.mall.domain.pojo.Fee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (pricing)数据Mapper
 *
 * @author kancy
 * @since 2022-10-26 14:15:39
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PricingMapper extends BaseMapper<Pricing> {
@Select("select sbu,price from pricing where cell_id=#{cell_id}")
    ArrayList<Fee> selectFeeList(@Param("cell_id") String cellId);
}
