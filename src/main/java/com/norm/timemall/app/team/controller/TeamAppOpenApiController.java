package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.team.domain.dto.TeamOpenFetchBrandRoleInfoDTO;
import com.norm.timemall.app.team.domain.vo.TeamOpenFetchBrandRoleInfoVO;
import com.norm.timemall.app.team.service.TeamAppOpenApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamAppOpenApiController {

    @Autowired
    private TeamAppOpenApiService teamAppOpenApiService;

    @GetMapping("/api/open/app/oasis/role")
    public TeamOpenFetchBrandRoleInfoVO fetchBrandRoleInfoInChannel(@Validated TeamOpenFetchBrandRoleInfoDTO dto){

        return teamAppOpenApiService.retrieveBrandRoleInfo(dto);

    }
}
