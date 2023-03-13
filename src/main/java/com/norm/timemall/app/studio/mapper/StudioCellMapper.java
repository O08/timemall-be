package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Cell;
import com.norm.timemall.app.base.pojo.ro.CellIntroRO;
import com.norm.timemall.app.base.pojo.ro.CellInfoRO;
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
public interface StudioCellMapper extends BaseMapper<Cell> {
    IPage<CellInfoRO> selectCellPage(IPage<CellInfoRO> page, @Param("brand_id") String brandId);
    void updatTitleById(@Param("id") String cellId,@Param("user_id") String userId, @Param("title") String title);
    @Update(value = "update cell set cover = #{uri} where id = #{id}")
    void updateCoverById(@Param("id") String cellId, @Param("uri") String uri);

    @Update(value = "update cell set intro_cover = #{uri} where id = #{id}")
    void updateIntroCoverById(@Param("id") String cellId, @Param("uri") String uri);

    @Update(value = "update cell set content = #{content} where id = #{id}")
    void updateCoverContentById(@Param("id") String cellId, @Param("content") String content);
    @Update(value = "update cell set mark = #{code} where id = #{id}")
    void updateMarkById(@Param("id")  String cellId, @Param("code")  String code);

    CellIntroRO selectCellProfileInfo(@Param("id")String cellId);

}

