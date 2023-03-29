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
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.team.constant.OasisConstant;
import com.norm.timemall.app.team.domain.dto.TeamNewOasisDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisGeneralDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisRiskDTO;
import com.norm.timemall.app.team.domain.pojo.TeamOasisAnnounce;
import com.norm.timemall.app.team.domain.pojo.TeamOasisIndex;
import com.norm.timemall.app.team.domain.pojo.TeamOasisIndexEntry;
import com.norm.timemall.app.team.domain.ro.TeamOasisRO;
import com.norm.timemall.app.team.mapper.TeamOasisIndMapper;
import com.norm.timemall.app.team.mapper.TeamOasisMapper;
import com.norm.timemall.app.team.mapper.TeamOasisMemberMapper;
import com.norm.timemall.app.team.service.TeamOasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamOasisServiceImpl implements TeamOasisService {

    @Autowired
    private TeamOasisMapper teamOasisMapper;
    @Autowired
    private TeamOasisMemberMapper teamOasisMemberMapper;
    @Autowired
    private TeamOasisIndMapper teamOasisIndMapper;

    @Autowired
    private AccountService accountService;

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
        teamOasisMapper.updateRiskById(dto.getOasisId(),dto.getRisk());
    }

    @Override
    public String newOasis(TeamNewOasisDTO dto) {
        String brandId = accountService.
                findBrandInfoByUserId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .getId();

        Oasis oasis = new Oasis();
        oasis.setId(IdUtil.simpleUUID())
                .setTitle(dto.getTitle())
                .setSubtitle(dto.getSubTitle())
                .setInitiatorId(brandId)
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
                .setBrandId(brandId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOasisMemberMapper.insert(member);

        return oasis.getId();

    }

    @Override
    public TeamOasisAnnounce findOasisAnnounce(String oasisId) {
        // todo
        TeamOasisAnnounce announce = teamOasisMapper.selectAnnounceById(oasisId);
        return  announce;
    }

    @Override
    public TeamOasisIndex findOasisValIndex(String oasisId) {
        ArrayList<TeamOasisIndexEntry> inds = teamOasisMapper.selectOasisValByOasisId(oasisId);
        TeamOasisIndex ro = new TeamOasisIndex();
        ro.setRecords(inds);
        return ro;
    }

    @Override
    public void modifyOasisAvatar(String oasisId, String uri) {
        teamOasisMapper.updateAvatarById(oasisId,uri);

    }

    @Override
    public void tagOasisTag(String oasisId, String mark) {
        teamOasisMapper.updateMarkById(oasisId,mark);
    }

    @Override
    public void modifyOasisBaseInfo(TeamOasisGeneralDTO dto) {
        teamOasisMapper.updateTitleAndSubTitleById(dto);
    }
}
