package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.DspCaseMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.pojo.TeamDspCaseMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (dsp_case_material)数据Mapper
 *
 * @author kancy
 * @since 2025-04-14 15:02:32
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamDspCaseMaterialMapper extends BaseMapper<DspCaseMaterial> {
@Select("select m.material_name,m.material_url from dsp_case_material m where m.case_no=#{case_no} and m.material_type=#{material_type} order by m.create_at desc")
    TeamDspCaseMaterial[] selectListByCaseNoAndType(@Param("case_no") String caseNO,@Param("material_type") String materialType);
}
