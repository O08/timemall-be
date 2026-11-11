package com.norm.timemall.app.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.Cell;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (cell)数据Mapper
 *
 * @author kancy
 * @since 2022-10-25 20:09:25
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BaseCellMapper extends BaseMapper<Cell> {
@Update("update cell set mark=#{mark} where brand_id=#{brand_id} and mark='2' ")
    void updateMarkByBrandId(@Param("mark") String mark,@Param("brand_id") String brandId);
}

