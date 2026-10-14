package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OfficeDepartment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamOfficeQueryDepartmentPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryDepartmentPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (office_department)数据Mapper
 *
 * @author kancy
 * @since 2025-11-18 11:53:55
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOfficeDepartmentMapper extends BaseMapper<OfficeDepartment> {

    IPage<TeamOfficeQueryDepartmentPageRO> selectPageByQ(IPage<TeamOfficeQueryDepartmentPageRO> page, @Param("dto") TeamOfficeQueryDepartmentPageDTO dto);
}
