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

    @Override
    public boolean alreadyOasisMember(String channel) {

        String brandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return  teamOasisMapper.selectCountChannel(channel,brandId)>0;


    }

    @Override
    public boolean validateChannelAdminRoleUseFeedId(String feedId) {

        String initiatorBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();

        return teamOasisMapper.selectCountByFeedIdAndInitiator(feedId,initiatorBrandId)>0;

    }

    @Override
    public boolean validateChannelAdminRoleUseChannelId(String channelId) {

        String initiatorBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();

        return teamOasisMapper.selectCountByChannelIdAndInitiator(channelId,initiatorBrandId);

    }
}
