package com.norm.timemall.app.team.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.team.domain.pojo.TeamTrans;
import com.norm.timemall.app.team.mapper.TeamTransactionsQueryMapperr;
import com.norm.timemall.app.team.service.TeamTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamTransServiceImpl implements TeamTransService {
    @Autowired
    private TeamTransactionsQueryMapperr teamTransactionsQueryMapper;
    @Override
    public IPage<TeamTrans>  findTrans(PageDTO dto) {
        IPage<TeamTrans>  page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());

        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return teamTransactionsQueryMapper.selectTransByBrand(page,currentBrandId, FidTypeEnum.BRAND.getMark());
    }
}
