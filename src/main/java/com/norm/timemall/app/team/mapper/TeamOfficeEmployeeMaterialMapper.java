package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OfficeEmployeeMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamOfficeFetchEmployeeMaterialPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeFetchEmployeeMaterialPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (office_employee_material)数据Mapper
 *
 * @author kancy
 * @since 2025-11-18 11:53:55
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOfficeEmployeeMaterialMapper extends BaseMapper<OfficeEmployeeMaterial> {

    IPage<TeamOfficeFetchEmployeeMaterialPageRO> selectPageByEmployeeId(IPage<TeamOfficeFetchEmployeeMaterialPageRO> page,@Param("dto") TeamOfficeFetchEmployeeMaterialPageDTO dto);
}
