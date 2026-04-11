package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OfficeEmployee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamOfficeQueryEmployeePageDTO;
import com.norm.timemall.app.team.domain.ro.OfficeEmployeeAndCompensationRO;
import com.norm.timemall.app.team.domain.ro.OfficeEmployeeRO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeFetchEmployeeBasicInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryEmployeePageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (office_employee)数据Mapper
 *
 * @author kancy
 * @since 2025-11-18 11:53:55
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOfficeEmployeeMapper extends BaseMapper<OfficeEmployee> {

    IPage<TeamOfficeQueryEmployeePageRO> selectPageByQ(IPage<TeamOfficeQueryEmployeePageRO> page, @Param("dto") TeamOfficeQueryEmployeePageDTO dto);
    @Select("select e.oasis_id,e.salary, e.id,e.employee_name,e.role,e.status,e.genre,d.title as department from office_employee e inner join office_department d on e.department_id=d.id where e.oasis_id=#{oasisId} and e.status=#{employeeStatus}")
    List<OfficeEmployeeRO> selectAllEmployeeByOasisIdAndStatus(@Param("oasisId") String oasisId,@Param("employeeStatus") String employeeStatus);

    List<OfficeEmployeeAndCompensationRO> selectEmployeeAndCompensationByOasisIdAndStatus(@Param("oasisId") String oasisId,@Param("employeeStatus") String employeeStatus);
    @Select("select e.oasis_id,e.salary, e.id,e.employee_name,e.role,e.status,e.genre,d.title as department from office_employee e inner join office_department d on e.department_id=d.id where e.id=#{employeeId}")
    OfficeEmployeeRO selectOneEmployeeById(@Param("employeeId") String employeeId);

    TeamOfficeFetchEmployeeBasicInfoRO selectBasicInfoById(@Param("id") String id);
}
