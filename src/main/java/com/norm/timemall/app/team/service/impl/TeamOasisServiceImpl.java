package com.norm.timemall.app.team.service.impl;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.OasisMarkEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.base.mo.OasisMember;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.team.constant.OasisConstant;
import com.norm.timemall.app.team.domain.dto.TeamNewOasisDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisRiskDTO;
import com.norm.timemall.app.team.domain.ro.TeamOasisRO;
import com.norm.timemall.app.team.mapper.TeamOasisMapper;
import com.norm.timemall.app.team.mapper.TeamOasisMemberMapper;
import com.norm.timemall.app.team.service.TeamOasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TeamOasisServiceImpl implements TeamOasisService {

    @Autowired
    private TeamOasisMapper teamOasisMapper;
    @Autowired
    private TeamOasisMemberMapper teamOasisMemberMapper;

    @Override
    public IPage<TeamOasisRO> findOasis(TeamOasisPageDTO dto) {
        IPage<TeamOasisRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamOasisMapper.selectPageByQ(page,dto.getQ());
    }

    @Override
    public Oasis findOneById(String oasisId) {
        return teamOasisMapper.selectById(oasisId);
    }

    @Override
    public void modifyOasisAnnounce(String oasisId, String uri) {
        teamOasisMapper.updateAnnounceById(oasisId,uri);
    }

    @Override
    public void modifyOasisRisk(TeamOasisRiskDTO dto) {
        Gson gson = new Gson();
        teamOasisMapper.updateRiskById(dto.getRisk().getOasisId(),gson.toJson(dto.getRisk().getRiskEntries()));
    }

    @Override
    public void newOasis(TeamNewOasisDTO dto) {
        CustomizeUser user = SecurityUserHelper.getCurrentPrincipal();

        Oasis oasis = new Oasis();
        oasis.setId(IdUtil.simpleUUID())
                .setTitle(dto.getTitle())
                .setSubtitle(dto.getSubTitle())
                .setInitiatorId(user.getUserId()) // todo change to brand id
                .setMark(OasisMarkEnum.CREATED.getMark())
                .setMembership(OasisConstant.INIT_MEMBERSHIP) // init
                .setMaxMembers(OasisConstant.INIT_MAXMEMBERS)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        // insert to oasis
        teamOasisMapper.insert(oasis);

        // insert to oasis_member
        OasisMember member = new OasisMember();
        member.setId(IdUtil.simpleUUID())
                .setOasisId(oasis.getId())
                .setBrandId(user.getUserId()) // todo change to brand id
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOasisMemberMapper.insert(member);

    }
}
