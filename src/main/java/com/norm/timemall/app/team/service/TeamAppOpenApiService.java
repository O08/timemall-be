package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.dto.TeamOpenFetchBrandRoleInfoDTO;
import com.norm.timemall.app.team.domain.vo.TeamOpenFetchBrandRoleInfoVO;
import org.springframework.stereotype.Service;

@Service
public interface TeamAppOpenApiService {
    TeamOpenFetchBrandRoleInfoVO retrieveBrandRoleInfo(TeamOpenFetchBrandRoleInfoDTO dto);
}
