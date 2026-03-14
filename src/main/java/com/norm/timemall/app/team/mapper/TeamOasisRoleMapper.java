package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.OasisRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamFetchOasisMemberRoleConfigurationDTO;
import com.norm.timemall.app.team.domain.dto.TeamFetchOasisRolesDTO;
import com.norm.timemall.app.team.domain.dto.TeamFetchRoleChannelDTO;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisMemberRoleConfigurationRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisRolesRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchRoleChannelRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * (oasis_role)数据Mapper
 *
 * @author kancy
 * @since 2025-08-23 11:24:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisRoleMapper extends BaseMapper<OasisRole> {

    ArrayList<TeamFetchOasisRolesRO> selectRoleByOasisId(@Param("dto") TeamFetchOasisRolesDTO dto);

    ArrayList<TeamFetchRoleChannelRO> selectRoleChannel(@Param(("dto")) TeamFetchRoleChannelDTO dto);

    ArrayList<TeamFetchOasisMemberRoleConfigurationRO> selectMemberRoleConfiguration(@Param("dto") TeamFetchOasisMemberRoleConfigurationDTO dto);

}
