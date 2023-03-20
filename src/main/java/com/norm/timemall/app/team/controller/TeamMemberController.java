package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.TeamInviteToOasisDTO;
import com.norm.timemall.app.team.domain.pojo.TeamOasisMember;
import com.norm.timemall.app.team.domain.ro.TeamOasisMemberRO;
import com.norm.timemall.app.team.domain.vo.TeamOasisMemberVO;
import com.norm.timemall.app.team.service.TeamOasisJoinService;
import com.norm.timemall.app.team.service.TeamOasisMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
@Validated
@RestController
public class TeamMemberController {
    @Autowired
    private TeamOasisMemberService teamOasisMemberService;

    @Autowired
    private TeamOasisJoinService teamOasisJoinService;
    /**
    * oasis 组员列表
    */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/member")
    public TeamOasisMemberVO retrieveOasisMember(@RequestParam @NotBlank(message = "oasisId is required")String oasisId){
      ArrayList<TeamOasisMemberRO> ros =  teamOasisMemberService.findOasisMember(oasisId);

      TeamOasisMember member = new TeamOasisMember();
      member.setRecords(ros);

      TeamOasisMemberVO vo = new TeamOasisMemberVO();
      vo.setMember(member);
      vo.setResponseCode(CodeEnum.SUCCESS);

      return vo;
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/invite")
    public SuccessVO inviteBrandJoinOasis(@Validated @RequestBody  TeamInviteToOasisDTO dto){
        teamOasisJoinService.inviteBrand(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
