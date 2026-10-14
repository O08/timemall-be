package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.mo.OasisFastLink;
import com.norm.timemall.app.team.domain.dto.TeamAddNewFastLinkDTO;
import com.norm.timemall.app.team.domain.ro.FetchFastLinkRO;
import com.norm.timemall.app.team.mapper.TeamOasisFastLinkMapper;
import com.norm.timemall.app.team.service.TeamOasisFastLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamOasisFastLinkServiceImpl implements TeamOasisFastLinkService {

    @Autowired
    private TeamOasisFastLinkMapper teamOasisFastLinkMapper;

    @Override
    public ArrayList<FetchFastLinkRO> findFastLinks(String id) {

        ArrayList<FetchFastLinkRO> links = teamOasisFastLinkMapper.selectListByOasisId(id);
        return links;

    }

    @Override
    public boolean validateLinkMaxLimit(String oasisId) {

        Long maxLimit= 8L;
        LambdaQueryWrapper<OasisFastLink> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OasisFastLink::getOasisId,oasisId);
        return  teamOasisFastLinkMapper.selectCount(wrapper) < maxLimit;

    }

    @Override
    public void addOneFastLink(TeamAddNewFastLinkDTO dto, String logoUrl) {

        OasisFastLink fastLink = new OasisFastLink();
        fastLink.setId(IdUtil.simpleUUID())
                .setTitle(dto.getTitle())
                .setLinkDetail(dto.getLinkDetail())
                .setLogo(logoUrl)
                .setOasisId(dto.getOasisId())
                .setLinkUrl(dto.getLinkUrl())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOasisFastLinkMapper.insert(fastLink);

    }



    @Override
    public void removeOneFastLink(String id) {
      teamOasisFastLinkMapper.deleteById(id);
    }

    @Override
    public OasisFastLink findOneFastLink(String id) {
        return teamOasisFastLinkMapper.selectById(id);
    }
}
