package com.norm.timemall.app.team.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.team.domain.dto.TeamTalentPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamTalentRO;
import com.norm.timemall.app.team.mapper.TeamBrandMapper;
import com.norm.timemall.app.team.service.TeamBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamBrandServiceImpl implements TeamBrandService {
    @Autowired
    private TeamBrandMapper teamBrandMapper;
    @Override
    public IPage<TeamTalentRO> findTalents(TeamTalentPageDTO dto) {
        IPage<TeamTalentRO> page  = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());

        return teamBrandMapper.selectPageByQ(page,dto.getQ());
    }
}
