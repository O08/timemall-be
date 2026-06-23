package com.norm.timemall.app.team.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.BluvarrierRoleEnum;
import com.norm.timemall.app.base.enums.CommercialPaperTagEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Bluvarrier;
import com.norm.timemall.app.base.mo.CommercialPaper;
import com.norm.timemall.app.team.mapper.TeamBluvarrierMapper;
import com.norm.timemall.app.team.mapper.TeamCommercialPaperMapper;
import com.norm.timemall.app.team.service.TeamDspActionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TeamDspActionsServiceImpl implements TeamDspActionsService {
    @Autowired
    private TeamCommercialPaperMapper teamCommercialPaperMapper;

    @Autowired
    private TeamBluvarrierMapper teamBluvarrierMapper;
    @Override
    public void doCloseCommercialPaper(String id) {

        validatedRoleAsPeacemaker();

        CommercialPaper commercialPaper = teamCommercialPaperMapper.selectById(id);
        if(commercialPaper==null){
            throw new QuickMessageException("未找到相关商单");
        }

        commercialPaper.setTag(CommercialPaperTagEnum.CLOSED.getMark());
        commercialPaper.setModifiedAt(new Date());
        teamCommercialPaperMapper.updateById(commercialPaper);


    }


    private void validatedRoleAsPeacemaker() {
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<Bluvarrier> lambdaQueryWrapper= Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Bluvarrier::getRoleCode, BluvarrierRoleEnum.HOSTING.getMark());
        Bluvarrier bluvarrier = teamBluvarrierMapper.selectOne(lambdaQueryWrapper);
        if(bluvarrier==null || !currentBrandId.equals(bluvarrier.getBrandId())){
            throw new QuickMessageException("权限校验不通过");
        }
    }
}
