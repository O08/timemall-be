package com.norm.timemall.app.mall.mapper;

import com.norm.timemall.app.base.mo.CellList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.mall.domain.ro.CellListRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (cell_list)数据Mapper
 *
 * @author kancy
 * @since 2022-10-26 10:10:11
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface CellListMapper extends BaseMapper<CellList> {

    CellListRO[] selectCellListByBrandId(@Param("brandId") String brandId);

}
