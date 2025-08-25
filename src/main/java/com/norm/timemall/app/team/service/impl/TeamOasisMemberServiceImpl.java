package com.norm.timemall.app.team.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.OasisMember;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.team.domain.dto.TeamFetchOasisMemberPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisMemberPageRO;
import com.norm.timemall.app.team.domain.ro.TeamOasisMemberRO;
import com.norm.timemall.app.team.mapper.TeamOasisMemberMapper;
import com.norm.timemall.app.team.service.TeamOasisMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TeamOasisMemberServiceImpl implements TeamOasisMemberService {

    @Autowired
    private TeamOasisMemberMapper teamOasisMemberMapper;
    @Autowired
    private AccountService accountService;
    @Override
    public ArrayList<TeamOasisMemberRO> findOasisMember(String oasisId) {
        return teamOasisMemberMapper.selectListByOasisId(oasisId);
    }

    @Override
    public void unfollowOasis(String oasisId,String brandId) {


        LambdaQueryWrapper<OasisMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OasisMember::getOasisId,oasisId)
                .eq(OasisMember::getBrandId,brandId);

        teamOasisMemberMapper.delete(wrapper);

    }

    @Override
    public IPage<TeamFetchOasisMemberPageRO> findOasisMemberAndRole(TeamFetchOasisMemberPageDTO dto) {
        IPage<TeamFetchOasisMemberPageRO>  page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamOasisMemberMapper.selectMemberAndRole(page,dto);
    }

    @Override
    public OasisMember findOneMember(String oasisId,String memberBrandId) {
        LambdaQueryWrapper<OasisMember> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(OasisMember::getOasisId,oasisId)
                .eq(OasisMember::getBrandId,memberBrandId);
        return teamOasisMemberMapper.selectOne(wrapper);
    }
}
