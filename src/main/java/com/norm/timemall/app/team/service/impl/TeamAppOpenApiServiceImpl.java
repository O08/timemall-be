package com.norm.timemall.app.team.service.impl;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.TeamOpenFetchBrandRoleInfoDTO;
import com.norm.timemall.app.team.domain.pojo.TeamOpenFetchBrandRoleInfo;
import com.norm.timemall.app.team.domain.vo.TeamOpenFetchBrandRoleInfoVO;
import com.norm.timemall.app.team.mapper.TeamOasisChannelMapper;
import com.norm.timemall.app.team.service.TeamAppOpenApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamAppOpenApiServiceImpl implements TeamAppOpenApiService {
    @Autowired
    private TeamOasisChannelMapper teamOasisChannelMapper;

    @Override
    public TeamOpenFetchBrandRoleInfoVO retrieveBrandRoleInfo(TeamOpenFetchBrandRoleInfoDTO dto) {

        boolean adminRole = teamOasisChannelMapper.validateAdminRole(dto.getChannel(), dto.getBrandId());
        String[] tier = { adminRole? "admin" : "visitor" };

        TeamOpenFetchBrandRoleInfoVO vo = new TeamOpenFetchBrandRoleInfoVO();
        TeamOpenFetchBrandRoleInfo role = new TeamOpenFetchBrandRoleInfo();
        role.setTier(tier);
        vo.setRole(role);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
