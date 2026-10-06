package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Cell;
import com.norm.timemall.app.base.pojo.ro.CellIntroRO;
import com.norm.timemall.app.base.pojo.ro.CellInfoRO;
import com.norm.timemall.app.studio.domain.dto.StudioCellIntroContentDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioCellOverView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * (cell)数据Mapper
 *
 * @author kancy
 * @since 2022-10-25 20:09:25
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioCellMapper extends BaseMapper<Cell> {
    IPage<CellInfoRO> selectCellPage(IPage<CellInfoRO> page, @Param("brand_id") String brandId);
    void updateTitleInvoiceTagById(@Param("id") String cellId, @Param("user_id") String userId, @Param("dto") StudioCellOverView dto );
    @Update(value = "update cell set cover = #{uri} where id = #{id}")
    void updateCoverById(@Param("id") String cellId, @Param("uri") String uri);

    @Update(value = "update cell set intro_cover = #{uri} where id = #{id}")
    void updateIntroCoverById(@Param("id") String cellId, @Param("uri") String uri);

    @Update(value = "update cell set product_desc = #{dto.productDesc} where id = #{dto.id}")
    void updateCoverContentById(@Param("dto") StudioCellIntroContentDTO dto);
    @Update(value = "update cell set mark = #{code} where id = #{id}")
    void updateMarkById(@Param("id")  String cellId, @Param("code")  String code);

    CellIntroRO selectCellProfileInfo(@Param("id")String cellId);

@Select("select c.* from cell c , cell_plan_order o where o.id=#{orderId} and c.id=o.cell_id")
    Cell selectCellByCellPlanOrderId(@Param("orderId") String orderId);
    @Select("select c.* from cell c , cell_plan_order o where o.id=#{orderId} and o.tag=#{tag} and c.id=o.cell_id")
    Cell selectCellByCellPlanOrderIdAndTag(@Param("orderId") String orderId,@Param("tag") String tag);
}

