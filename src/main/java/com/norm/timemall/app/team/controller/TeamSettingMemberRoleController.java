package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.TeamFetchOasisMemberPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisMemberPageRO;
import com.norm.timemall.app.team.domain.vo.TeamFetchOasisMemberPageVO;
import com.norm.timemall.app.team.service.TeamOasisMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamSettingMemberRoleController {
    @Autowired
    private TeamOasisMemberService teamOasisMemberService;
    @GetMapping("/api/v1/team/oasis/member/query")
    public TeamFetchOasisMemberPageVO fetchOasisMember(@Validated TeamFetchOasisMemberPageDTO dto){
        IPage<TeamFetchOasisMemberPageRO> member=teamOasisMemberService.findOasisMemberAndRole(dto);
        TeamFetchOasisMemberPageVO vo = new TeamFetchOasisMemberPageVO();
        vo.setMember(member);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

}
