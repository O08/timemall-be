package com.norm.timemall.app.team.service.impl;

import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.team.mapper.TeamOasisMapper;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamDataPolicyServiceImpl implements TeamDataPolicyService {
    @Autowired
    private TeamOasisMapper teamOasisMapper;
    @Autowired
    private AccountService accountService;
    @Override
    public boolean passIfBrandIsCreatorOfOasis(String oasisId) {
        String brandId = accountService.
                findBrandInfoByUserId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .getId();
        Oasis oasis = teamOasisMapper.selectById(oasisId);

        return oasis !=null && oasis.getInitiatorId().equals(brandId);
    }
}
