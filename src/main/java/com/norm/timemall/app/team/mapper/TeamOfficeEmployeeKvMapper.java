package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OfficeEmployeeKv;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamOfficeFetchEmployeeKvPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeFetchEmployeeKvPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (office_employee_kv)数据Mapper
 *
 * @author kancy
 * @since 2025-11-18 11:53:55
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOfficeEmployeeKvMapper extends BaseMapper<OfficeEmployeeKv> {

    IPage<TeamOfficeFetchEmployeeKvPageRO> selectPageByEmployeeId(IPage<TeamOfficeFetchEmployeeKvPageRO> page, @Param("dto") TeamOfficeFetchEmployeeKvPageDTO dto);
}
