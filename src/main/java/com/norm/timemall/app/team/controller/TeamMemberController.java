package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.team.domain.dto.TeamInviteToOasisDTO;
import com.norm.timemall.app.team.domain.pojo.TeamOasisMember;
import com.norm.timemall.app.team.domain.ro.TeamOasisMemberRO;
import com.norm.timemall.app.team.domain.vo.TeamOasisMemberVO;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import com.norm.timemall.app.team.service.TeamGroupMemberRelService;
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

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TeamGroupMemberRelService teamGroupMemberRelService;
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
    /**
     * 退出oasis
     */
    @ResponseBody
    @DeleteMapping(value = "/api/v1/team/oasis/unfollow")
    public SuccessVO unfollowOasis(@RequestParam("oasisId") String oasisId){
        String brandId = accountService.
                findBrandInfoByUserId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .getId();
        // remove from oasis join tbl
        teamOasisJoinService.unfollowOasis(oasisId,brandId);
        // remove from oasis member tbl
        teamOasisMemberService.unfollowOasis(oasisId,brandId);
        // remove from group_member_rel tbl
        teamGroupMemberRelService.unfollowChannel(oasisId);

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    /**
     * 移出oasis
     */
    @ResponseBody
    @DeleteMapping(value = "/api/v1/team/oasis/remove_member")
    public SuccessVO unfollowOasis(@RequestParam("oasisId") String oasisId,
                                   @RequestParam("brandId") String brandId){
        // check oasis ,if role is oasis creator ,pass
        boolean pass = teamDataPolicyService.passIfBrandIsCreatorOfOasis(oasisId);
        if(pass){
            // remove from oasis join tbl
            teamOasisJoinService.unfollowOasis(oasisId,brandId);
            // remove from oasis member tbl
            teamOasisMemberService.unfollowOasis(oasisId,brandId);

            // remove from group_member_rel tbl
            String memberUserId= accountService.findBrandInfoByBrandId(brandId).getCustomerId();
            teamGroupMemberRelService.removeMemberFromChannel(oasisId,memberUserId);
        }


        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
