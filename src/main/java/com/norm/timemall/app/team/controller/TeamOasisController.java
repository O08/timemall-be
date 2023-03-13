package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.team.domain.dto.TeamNewOasisDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisRiskDTO;
import com.norm.timemall.app.team.domain.pojo.TeamInvitedOasis;
import com.norm.timemall.app.team.domain.ro.TeamInviteRO;
import com.norm.timemall.app.team.domain.ro.TeamOasisRO;
import com.norm.timemall.app.team.domain.vo.TeamInviteVO;
import com.norm.timemall.app.team.domain.vo.TeamOasisPageVO;
import com.norm.timemall.app.team.service.TeamOasisJoinService;
import com.norm.timemall.app.team.service.TeamOasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
public class TeamOasisController {

    @Autowired
    private TeamOasisService teamOasisService;

    @Autowired
    private TeamOasisJoinService teamOasisJoinService;
    @Autowired
    private FileStoreService fileStoreService;
    /**
     * 获取oasis列表
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/oasis")
    public TeamOasisPageVO retrieveOasis(@Validated TeamOasisPageDTO pageDTO){
        IPage<TeamOasisRO>  oasis = teamOasisService.findOasis(pageDTO);
        TeamOasisPageVO pageVO = new TeamOasisPageVO();
        pageVO.setOasis(oasis);
        pageVO.setResponseCode(CodeEnum.SUCCESS);
        return pageVO;
    }
    /**
     * 获取受邀请的oasis列表
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/invite")
    public TeamInviteVO retrieveInvitedOasis(){
        ArrayList<TeamInviteRO> invitedRO = teamOasisJoinService.findInvitedOasis();
        TeamInvitedOasis invited = new TeamInvitedOasis();
        invited.setRecords(invitedRO);
        TeamInviteVO vo = new TeamInviteVO();
        vo.setInvited(invited);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @ResponseBody
    @PutMapping(value = "/api/v1/team/join")
    public SuccessVO joinAOasis(String oasisId){
        teamOasisJoinService.acceptOasisInvitation(oasisId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/announce/{oasisId}")
    public SuccessVO uploadOasisAnnounce(@PathVariable(value = "oasisId") String oasisId,@RequestParam("file") MultipartFile file){
       // find oasis
        Oasis oasis = teamOasisService.findOneById(oasisId);
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.OASIS_ANNOUNCE);
        // update oasis announce uri
        teamOasisService.modifyOasisAnnounce(oasisId,uri);
        // delete unused file
        fileStoreService.deleteFile(oasis.getAnnounce());

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/risk")
    public SuccessVO modifyOasisRisk(TeamOasisRiskDTO dto){
        teamOasisService.modifyOasisRisk(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PostMapping(value = "/api/v1/team/oasis/new")
    public SuccessVO newOasis(@RequestBody TeamNewOasisDTO dto){
        teamOasisService.newOasis(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }


}
