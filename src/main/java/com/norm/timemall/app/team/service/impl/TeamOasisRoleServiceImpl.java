package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.OasisRoleCoreEnum;
import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.mo.OasisChannel;
import com.norm.timemall.app.base.mo.OasisMemberRole;
import com.norm.timemall.app.base.mo.OasisRole;
import com.norm.timemall.app.base.mo.OasisRoleChannel;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisMemberRoleConfigurationRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisRolesRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchRoleChannelRO;
import com.norm.timemall.app.team.domain.ro.TeamGetChannelOwnedByRoleRO;
import com.norm.timemall.app.team.domain.vo.TeamFetchOasisMemberRoleConfigurationVO;
import com.norm.timemall.app.team.domain.vo.TeamFetchOasisRolesVO;
import com.norm.timemall.app.team.domain.vo.TeamFetchRoleChannelVO;
import com.norm.timemall.app.team.domain.vo.TeamGetChannelOwnedByRoleVO;
import com.norm.timemall.app.team.mapper.TeamOasisChannelMapper;
import com.norm.timemall.app.team.mapper.TeamOasisMemberRoleMapper;
import com.norm.timemall.app.team.mapper.TeamOasisRoleChannelMapper;
import com.norm.timemall.app.team.mapper.TeamOasisRoleMapper;
import com.norm.timemall.app.team.service.TeamOasisRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamOasisRoleServiceImpl implements TeamOasisRoleService {
    @Autowired
    private TeamOasisRoleMapper teamOasisRoleMapper;
    @Autowired
    private TeamOasisMemberRoleMapper teamOasisMemberRoleMapper;
    @Autowired
    private TeamOasisChannelMapper teamOasisChannelMapper;
    @Autowired
    private TeamOasisRoleChannelMapper teamOasisRoleChannelMapper;
    @Override
    public TeamFetchOasisRolesVO findOasisRoles(TeamFetchOasisRolesDTO dto) {
        ArrayList<TeamFetchOasisRolesRO> role= teamOasisRoleMapper.selectRoleByOasisId(dto);
        TeamFetchOasisRolesVO vo =new TeamFetchOasisRolesVO();
        vo.setRole(role);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public void newOasisRole(TeamCreateOasisRoleDTO dto) {
        // check role code
        LambdaQueryWrapper<OasisRole> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(OasisRole::getOasisId,dto.getOasisId())
                .eq(OasisRole::getRoleCode,dto.getRoleCode());
        boolean roleCodeExists = teamOasisRoleMapper.exists(wrapper);
        if(roleCodeExists){
            throw new QuickMessageException("身份组编码已注册，操作失败");
        }
        OasisRole role = new OasisRole();
        role.setId(IdUtil.simpleUUID())
                .setOasisId(dto.getOasisId())
                .setRoleCode(dto.getRoleCode())
                .setRoleName(dto.getRoleName())
                .setRoleDesc(dto.getRoleDesc())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOasisRoleMapper.insert(role);

    }

    @Override
    public OasisRole findOneRole(String roleId) {
        return teamOasisRoleMapper.selectById(roleId);
    }

    @Override
    public void changeRole(TeamEditOasisRoleDTO dto) {
        LambdaUpdateWrapper<OasisRole> wrapper=Wrappers.lambdaUpdate();
        wrapper.eq(OasisRole::getId,dto.getRoleId());

        OasisRole targetRole=new OasisRole();
        targetRole.setId(dto.getRoleId())
                .setRoleName(dto.getRoleName())
                .setRoleDesc(dto.getRoleDesc())
                .setModifiedAt(new Date());

        teamOasisRoleMapper.update(targetRole,wrapper);

    }

    @Override
    public void delRole(String id) {
        // public and admin can't delete
        OasisRole role = teamOasisRoleMapper.selectById(id);
        if(OasisRoleCoreEnum.ADMIN.getMark().equals(role.getRoleCode())
                ||OasisRoleCoreEnum.PUBLIC.getMark().equals(role.getRoleCode())){
            throw new QuickMessageException("身份组不支持删除操作");
        }
        // if have user have role ,can't delete
        LambdaQueryWrapper<OasisMemberRole> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(OasisMemberRole::getOasisRoleId,id);

        boolean existsGrantRecord = teamOasisMemberRoleMapper.exists(wrapper);
        if(existsGrantRecord){
            throw new QuickMessageException("身份组正在被部分成员使用，请取消相关授权后进行删除操作");
        }
        // if  bind to channel ,can't delete
        LambdaQueryWrapper<OasisRoleChannel> roleWrapper=Wrappers.lambdaQuery();
        roleWrapper.eq(OasisRoleChannel::getOasisRoleId,id);
        boolean existsChannelRecord = teamOasisRoleChannelMapper.exists(roleWrapper);
        if(existsChannelRecord){
            throw new QuickMessageException("身份组已分配权限，请取消权限后进行删除操作");
        }
        teamOasisRoleMapper.deleteById(id);
    }

    @Override
    public TeamFetchRoleChannelVO findRoleChannel(TeamFetchRoleChannelDTO dto) {
        ArrayList<TeamFetchRoleChannelRO> channel= teamOasisRoleMapper.selectRoleChannel(dto);
        TeamFetchRoleChannelVO vo = new TeamFetchRoleChannelVO();
        vo.setChannel(channel);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public void toggleChannelPermission(TeamOasisRoleConfigChannelDTO dto) {

        LambdaQueryWrapper<OasisRoleChannel> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(OasisRoleChannel::getOasisRoleId,dto.getRoleId())
                .eq(OasisRoleChannel::getOasisChannelId,dto.getOasisChannelId());
        teamOasisRoleChannelMapper.delete(wrapper);

        if(SwitchCheckEnum.ENABLE.getMark().equals(dto.getAssigned())){
            OasisRoleChannel permission=new OasisRoleChannel();
            permission.setId(IdUtil.simpleUUID())
                    .setOasisRoleId(dto.getRoleId())
                    .setOasisChannelId(dto.getOasisChannelId())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamOasisRoleChannelMapper.insert(permission);
        }
    }

    @Override
    public OasisChannel finOneChannel(String oasisChannelId) {

        return teamOasisChannelMapper.selectById(oasisChannelId);
    }

    @Override
    public TeamGetChannelOwnedByRoleVO findChannelOwnedByRole(String id) {
        ArrayList<TeamGetChannelOwnedByRoleRO> channel=teamOasisRoleChannelMapper.selectChannelByRoleId(id);
        TeamGetChannelOwnedByRoleVO vo =new TeamGetChannelOwnedByRoleVO();
        vo.setChannel(channel);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public TeamFetchOasisMemberRoleConfigurationVO findOasisMemberRoleConfiguration(TeamFetchOasisMemberRoleConfigurationDTO dto) {
        ArrayList<TeamFetchOasisMemberRoleConfigurationRO> role=teamOasisRoleMapper.selectMemberRoleConfiguration(dto);
        TeamFetchOasisMemberRoleConfigurationVO vo = new TeamFetchOasisMemberRoleConfigurationVO();
        vo.setRole(role);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public void toggleMemberRole(String oasisId,TeamOasisMemberRoleConfigDTO dto) {
         LambdaQueryWrapper<OasisMemberRole> wrapper=Wrappers.lambdaQuery();
         wrapper.eq(OasisMemberRole::getOasisRoleId,dto.getRoleId())
                 .eq(OasisMemberRole::getMemberBrandId,dto.getMemberBrandId());

         teamOasisMemberRoleMapper.delete(wrapper);
        if(SwitchCheckEnum.ENABLE.getMark().equals(dto.getAssigned())){
            OasisMemberRole memberRole=new OasisMemberRole();
            memberRole.setId(IdUtil.simpleUUID())
                    .setOasisId(oasisId)
                    .setMemberBrandId(dto.getMemberBrandId())
                    .setOasisRoleId(dto.getRoleId())
                    .setStartsAt(new Date())
                    .setEndsAt(DateUtil.offsetMonth(new Date(),4096))
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamOasisMemberRoleMapper.insert(memberRole);
        }
    }
}
