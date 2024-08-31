package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.OasisChannelTagEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.MiniAppLibrary;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.base.mo.OasisChannel;
import com.norm.timemall.app.team.domain.dto.GetAppDTO;
import com.norm.timemall.app.team.domain.ro.FetchOasisAppListRO;
import com.norm.timemall.app.team.mapper.TeamMiniAppLibraryMapper;
import com.norm.timemall.app.team.mapper.TeamOasisChannelMapper;
import com.norm.timemall.app.team.mapper.TeamOasisMapper;
import com.norm.timemall.app.team.service.TeamMiniAppLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamMiniAppLibraryServiceImpl implements TeamMiniAppLibraryService {
    @Autowired
    private TeamMiniAppLibraryMapper teamMiniAppLibraryMapper;
    @Autowired
    private TeamOasisChannelMapper teamOasisChannelMapper;
    @Autowired
    private TeamOasisMapper teamOasisMapper;

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

        OasisChannel channel=new OasisChannel();
        channel.setId(IdUtil.simpleUUID())
                .setAppId(dto.getAppId())
                .setOasisId(dto.getOasisId())
                .setChannelName(miniApp.getAppName())
                .setChannelDesc(miniApp.getAppDesc())
                .setChannelTag(OasisChannelTagEnum.ONLINE.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOasisChannelMapper.insert(channel);

    }
}
