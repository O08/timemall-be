package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OfficeEmployeeCompensation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamOfficeFetchEmployeeCompensationConfigInfoPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeFetchEmployeeCompensationInfoPageDTO;
import com.norm.timemall.app.team.domain.ro.OfficeEmployeeCompensationRO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeFetchEmployeeCompensationConfigInfoPageRO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeFetchEmployeeCompensationInfoPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (office_employee_compensation)数据Mapper
 *
 * @author kancy
 * @since 2025-11-18 11:53:55
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOfficeEmployeeCompensationMapper extends BaseMapper<OfficeEmployeeCompensation> {
    @Select("select c.title,c.amount,c.direction from office_employee_compensation ec inner join office_compensation c on ec.compensation_id=c.id and c.status='1' where ec.employee_id=#{employeeId}")
    ArrayList<OfficeEmployeeCompensationRO> selectListByEmployeeId(@Param("employeeId") String employeeId);

    IPage<TeamOfficeFetchEmployeeCompensationInfoPageRO> selectEmployeeCompensationInfoByEmployeeId(IPage<TeamOfficeFetchEmployeeCompensationInfoPageRO> page, @Param("dto") TeamOfficeFetchEmployeeCompensationInfoPageDTO dto);

    IPage<TeamOfficeFetchEmployeeCompensationConfigInfoPageRO> selectEmployeeCompensationConfigInfoByEmployeeId(IPage<TeamOfficeFetchEmployeeCompensationConfigInfoPageRO> page,@Param("dto") TeamOfficeFetchEmployeeCompensationConfigInfoPageDTO dto);

}
