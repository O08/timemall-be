package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.Cell;
import com.norm.timemall.app.base.pojo.ro.CellInfoRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * (cell)数据Mapper
 *
 * @author kancy
 * @since 2022-10-25 20:09:25
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamCellMapper extends BaseMapper<Cell> {

@Select("select b.brand_name brand,b.avator,c.title,p.price,c.id,c.mark, p.sbu\n" +
        "        from cell c\n" +
        "        inner join brand b on c.brand_id = b.id\n" +
        "        inner join pricing p on c.id = p.cell_id\n" +
        "        where c.id = #{cell_id}\n" +
        "        and p.sbu = #{sbu}")
    CellInfoRO selectCellByIdAndSbu(@Param("cell_id") String cellId, @Param("sbu") String cellSbu);
}

