package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.TeamFetchFriendListDTO;
import com.norm.timemall.app.team.domain.pojo.TeamFetchFriendList;
import com.norm.timemall.app.team.domain.pojo.TeamInvitedOasis;
import com.norm.timemall.app.team.domain.ro.TeamInviteRO;
import com.norm.timemall.app.team.domain.vo.TeamFetchFriendListVO;
import com.norm.timemall.app.team.domain.vo.TeamInviteVO;
import com.norm.timemall.app.team.service.TeamOasisJoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;

@Validated
@RestController
public class TeamOasisInvitationController {

    @Autowired
    private TeamOasisJoinService teamOasisJoinService;
    /**
     * 获取受邀请的oasis列表
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/invitedOases")
    public TeamInviteVO retrieveInvitedOasis(@RequestParam @NotBlank(message = "brandId is required") String brandId){
        ArrayList<TeamInviteRO> invitedRO = teamOasisJoinService.findInvitedOasis(brandId);
        TeamInvitedOasis invited = new TeamInvitedOasis();
        invited.setRecords(invitedRO);
        TeamInviteVO vo = new TeamInviteVO();
        vo.setInvited(invited);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/acceptOasisInvitation")
    public SuccessVO joinAOasis(@RequestParam @NotBlank(message = "id is required") String id){
        teamOasisJoinService.acceptOasisInvitation(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @DeleteMapping(value = "/api/v1/team/oasis_join/{id}/remove")
    public SuccessVO delOasisJoinInvitation(@PathVariable("id") String id){
        teamOasisJoinService.removeOasisInvitation(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/team/oasis/friends")
    public TeamFetchFriendListVO fetchFriendList(@Validated TeamFetchFriendListDTO dto ){

        TeamFetchFriendList friend= teamOasisJoinService.findFriendThatNotInOasis(dto);
        TeamFetchFriendListVO vo = new TeamFetchFriendListVO();
        vo.setFriend(friend);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
}
