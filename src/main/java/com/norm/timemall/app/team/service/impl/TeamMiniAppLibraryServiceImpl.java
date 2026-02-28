package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.OasisChannelTagEnum;
import com.norm.timemall.app.base.enums.OasisRoleCoreEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.team.domain.dto.GetAppDTO;
import com.norm.timemall.app.team.domain.ro.FetchOasisAppListRO;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamMiniAppLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Service
public class TeamMiniAppLibraryServiceImpl implements TeamMiniAppLibraryService {
    @Autowired
    private TeamMiniAppLibraryMapper teamMiniAppLibraryMapper;
    @Autowired
    private TeamOasisChannelMapper teamOasisChannelMapper;
    @Autowired
    private TeamOasisMapper teamOasisMapper;
    @Autowired
    private TeamOasisRoleMapper teamOasisRoleMapper;
    @Autowired
    private TeamOasisRoleChannelMapper teamOasisRoleChannelMapper;

    @Override
    public ArrayList<FetchOasisAppListRO> findAppList() {
        return teamMiniAppLibraryMapper.selectAppList();
    }

    @Override
    public void installAppToOasis(GetAppDTO dto) {
        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Oasis oasis = teamOasisMapper.selectById(dto.getOasisId());
        if(oasis==null || !oasis.getInitiatorId().equals(brandId)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        MiniAppLibrary miniApp = teamMiniAppLibraryMapper.selectById(dto.getAppId());
        if(miniApp==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String och=IdUtil.simpleUUID();
        OasisChannel channel=new OasisChannel();
        channel.setId(och)
                .setAppId(dto.getAppId())
                .setOasisId(dto.getOasisId())
                .setChannelName(miniApp.getAppName())
                .setChannelDesc(miniApp.getAppDesc())
                .setChannelTag(OasisChannelTagEnum.ONLINE.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOasisChannelMapper.insert(channel);

        ArrayList<String> sortArr = oasis.getChannelSort() == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(new GsonBuilder().create().fromJson(oasis.getChannelSort().toString(), String[].class)));
        sortArr.add(och);
        oasis.setChannelSort(new Gson().toJson(sortArr));

        teamOasisMapper.updateById(oasis);

        addChannelToAdminRole(oasis.getId(),och);

    }

    private void addChannelToAdminRole(String oasisId,String channel){
        LambdaQueryWrapper<OasisRole> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(OasisRole::getRoleCode, OasisRoleCoreEnum.ADMIN.getMark())
                .eq(OasisRole::getOasisId,oasisId);
        OasisRole role = teamOasisRoleMapper.selectOne(wrapper);

        OasisRoleChannel rc=new OasisRoleChannel();
        rc.setId(IdUtil.simpleUUID())
                .setOasisRoleId(role.getId())
                .setOasisChannelId(channel)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOasisRoleChannelMapper.insert(rc);
    }
}
