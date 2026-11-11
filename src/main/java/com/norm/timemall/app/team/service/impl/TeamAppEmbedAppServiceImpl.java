package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.AppEmbedWeb;
import com.norm.timemall.app.team.domain.dto.TeamAppEmbedWebSettingDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppFetchEmbedWebChannelInfoRO;
import com.norm.timemall.app.team.mapper.TeamAppEmbedWebMapper;
import com.norm.timemall.app.team.mapper.TeamOasisChannelMapper;
import com.norm.timemall.app.team.service.TeamAppEmbedAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TeamAppEmbedAppServiceImpl implements TeamAppEmbedAppService {
    @Autowired
    private TeamAppEmbedWebMapper teamAppEmbedWebMapper;

    @Autowired
    private TeamOasisChannelMapper teamOasisChannelMapper;

    @Override
    public TeamAppFetchEmbedWebChannelInfoRO fetchEmbedWebChannelInfo(String channelId) {
        // 联表查询嵌入 web 配置与频道信息
        TeamAppFetchEmbedWebChannelInfoRO ro = teamAppEmbedWebMapper.selectEmbedWebChannelInfo(channelId);
        if (ro == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        return ro;
    }

    @Override
    public void modifyEmbedWebSetting(TeamAppEmbedWebSettingDTO dto) {

        LambdaQueryWrapper<AppEmbedWeb> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppEmbedWeb::getOasisChannelId, dto.getChannelId());
        AppEmbedWeb exist = teamAppEmbedWebMapper.selectOne(wrapper);
        if (exist == null) {
            AppEmbedWeb embedWeb = new AppEmbedWeb();
            embedWeb.setId(IdUtil.simpleUUID())
                    .setOasisChannelId(dto.getChannelId())
                    .setWebUri(dto.getWebUri())
                    .setCreatedAt(new Date())
                    .setModifiedAt(new Date());
            teamAppEmbedWebMapper.insert(embedWeb);
        } else {
            AppEmbedWeb update = new AppEmbedWeb();
            update.setWebUri(dto.getWebUri())
                    .setModifiedAt(new Date());
            teamAppEmbedWebMapper.update(update, wrapper);
        }
    }

    @Override
    public void removeChannelData(String channelId) {
        LambdaQueryWrapper<AppEmbedWeb> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppEmbedWeb::getOasisChannelId, channelId);
        teamAppEmbedWebMapper.delete(wrapper);
    }
}
