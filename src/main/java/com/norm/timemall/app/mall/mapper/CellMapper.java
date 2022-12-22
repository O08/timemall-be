package com.norm.timemall.app.mall.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.mall.domain.dto.BrandCellsPageDTO;
import com.norm.timemall.app.mall.domain.dto.CellPageDTO;
import com.norm.timemall.app.base.mo.Cell;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.pojo.ro.CellIntroRO;
import com.norm.timemall.app.mall.domain.pojo.MallHomeInfo;
import com.norm.timemall.app.mall.domain.ro.CellRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (cell)数据Mapper
 *
 * @author kancy
 * @since 2022-10-25 20:09:25
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface CellMapper extends BaseMapper<Cell> {
    IPage<CellRO> selectCellPage(Page<CellRO> page, @Param("dto") CellPageDTO cellPageDTO);

    CellIntroRO selectCellIntro(@Param("id") String cellId);

    IPage<CellRO> selectBrandCellPage(Page<CellRO> page, @Param("dto") BrandCellsPageDTO dto);

    MallHomeInfo selectHomeInfoByBrandId(@Param("id") String brandId);
}

