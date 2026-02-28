package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.OasisHtmlApp;
import com.norm.timemall.app.team.domain.dto.EditHtmlContentDTO;
import com.norm.timemall.app.team.mapper.TeamOasisChannelMapper;
import com.norm.timemall.app.team.mapper.TeamOasisHtmlAppMapper;
import com.norm.timemall.app.team.service.TeamOasisHtmlAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TeamOasisHtmlAppServiceImpl implements TeamOasisHtmlAppService {
    @Autowired
    private TeamOasisHtmlAppMapper teamOasisHtmlAppMapper;
    @Autowired
    private TeamOasisChannelMapper teamOasisChannelMapper;
    @Override
    public String findHtmlCode(String oasisChannelId) {
        LambdaQueryWrapper<OasisHtmlApp> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(OasisHtmlApp::getOasisChannelId,oasisChannelId);
        OasisHtmlApp oasisHtmlApp = teamOasisHtmlAppMapper.selectOne(wrapper);

        if(!SecurityUserHelper.alreadyLogin()){
            return oasisHtmlApp==null? "" : oasisHtmlApp.getHtmlCode();
        }

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(oasisHtmlApp==null && teamOasisChannelMapper.validateAdminRole(oasisChannelId,brandId)){
            initHtmlAppChannel(oasisChannelId);
        }
        return oasisHtmlApp==null? "" : oasisHtmlApp.getHtmlCode();
    }

    @Override
    public void modifyHtmlContent(EditHtmlContentDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean haveAdminRole= teamOasisChannelMapper.validateAdminRole(dto.getOasisChannelId(),brandId);
        if(!haveAdminRole){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        LambdaUpdateWrapper<OasisHtmlApp> wrapper=Wrappers.lambdaUpdate();
        wrapper.eq(OasisHtmlApp::getOasisChannelId,dto.getOasisChannelId());
        OasisHtmlApp oasisHtmlApp= new OasisHtmlApp();
        oasisHtmlApp.setOasisChannelId(dto.getOasisChannelId())
                .setHtmlCode(dto.getHtml())
                .setModifiedAt(new Date());

        teamOasisHtmlAppMapper.update(oasisHtmlApp,wrapper);

    }

    @Override
    public void removeChannelData(String id) {

        LambdaQueryWrapper<OasisHtmlApp> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OasisHtmlApp::getOasisChannelId,id);
        teamOasisHtmlAppMapper.delete(wrapper);

    }

    private void initHtmlAppChannel(String oasisChannelId){

        OasisHtmlApp oasisHtmlApp= new OasisHtmlApp();
        oasisHtmlApp.setId(IdUtil.simpleUUID())
                .setOasisChannelId(oasisChannelId)
                .setHtmlCode("")
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOasisHtmlAppMapper.insert(oasisHtmlApp);

    }
}
