package com.norm.timemall.app.team.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.base.mo.OasisChannel;
import com.norm.timemall.app.base.mo.OasisHtmlApp;
import com.norm.timemall.app.team.domain.dto.PutChannelGeneralDTO;
import com.norm.timemall.app.team.domain.dto.RefreshOasisChannelSortDTO;
import com.norm.timemall.app.team.domain.ro.FetchOasisChannelListRO;
import com.norm.timemall.app.team.domain.ro.FetchOneOasisChannelGeneralInfoRO;
import com.norm.timemall.app.team.mapper.TeamOasisChannelMapper;
import com.norm.timemall.app.team.mapper.TeamOasisHtmlAppMapper;
import com.norm.timemall.app.team.mapper.TeamOasisMapper;
import com.norm.timemall.app.team.service.TeamOasisChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Service
public class TeamOasisChannelServiceImpl implements TeamOasisChannelService {
    @Autowired
    private TeamOasisChannelMapper teamOasisChannelMapper;
    @Autowired
    private TeamOasisHtmlAppMapper teamOasisHtmlAppMapper;

    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Override
    public ArrayList<FetchOasisChannelListRO> findOasisChannelList(String oasisId) {
        return teamOasisChannelMapper.selectChannelListByOasisId(oasisId);
    }

    @Override
    public void removeOasisChannel(String oasisChannelId) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        OasisChannel oasisChannel = teamOasisChannelMapper.selectById(oasisChannelId);
        if(oasisChannel==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        Oasis oasis = teamOasisMapper.selectById(oasisChannel.getOasisId());
        if(oasis==null || !oasis.getInitiatorId().equals(brandId)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // remove from oasis channel
        teamOasisChannelMapper.deleteById(oasisChannelId);
        // remove from html app content record
        LambdaQueryWrapper<OasisHtmlApp> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(OasisHtmlApp::getOasisChannelId,oasisChannelId);
        teamOasisHtmlAppMapper.delete(wrapper);

        // update channel sort
        ArrayList<String> sortArr = oasis.getChannelSort() == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(new GsonBuilder().create().fromJson(oasis.getChannelSort().toString(), String[].class)));
        sortArr.remove(oasisChannelId);

        oasis.setChannelSort(new Gson().toJson(sortArr));

        teamOasisMapper.updateById(oasis);

    }

    @Override
    public void modifyChannelGeneralInfo(PutChannelGeneralDTO dto) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean haveAdminRole= teamOasisChannelMapper.validateAdminRole(dto.getOasisChannelId(),brandId);
        if(!haveAdminRole){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        LambdaUpdateWrapper<OasisChannel> wrapper=Wrappers.lambdaUpdate();
        wrapper.eq(OasisChannel::getId,dto.getOasisChannelId());
        OasisChannel oasisChannel=new OasisChannel();
        oasisChannel.setId(dto.getOasisChannelId())
                .setChannelName(dto.getChannelName())
                .setChannelDesc(dto.getChannelDesc())
                .setModifiedAt(new Date());
        teamOasisChannelMapper.update(oasisChannel,wrapper);

    }

    @Override
    public FetchOneOasisChannelGeneralInfoRO findOasisOneChannelGeneralInfo(String och) {
        return teamOasisChannelMapper.selectGeneralById(och);
    }

    @Override
    public void modifyChannelSortInfo(RefreshOasisChannelSortDTO dto) {
        boolean validated = JSONUtil.isTypeJSON(dto.getSortJson());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Oasis oasis = teamOasisMapper.selectById(dto.getOasisId());
        if(oasis==null || !oasis.getInitiatorId().equals(brandId)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        oasis.setChannelSort(dto.getSortJson());
        teamOasisMapper.updateById(oasis);

    }

    @Override
    public ArrayList<String> findChannelSort(String oasisId) {

        Oasis oasis = teamOasisMapper.selectById(oasisId);
        return oasis.getChannelSort() == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(new GsonBuilder().create().fromJson(oasis.getChannelSort().toString(), String[].class)));

    }
}