package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.team.domain.dto.TeamNewOasisDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisRiskDTO;
import com.norm.timemall.app.team.domain.pojo.TeamInvitedOasis;
import com.norm.timemall.app.team.domain.pojo.TeamJoinedOasis;
import com.norm.timemall.app.team.domain.pojo.TeamOasisAnnounce;
import com.norm.timemall.app.team.domain.pojo.TeamOasisIndex;
import com.norm.timemall.app.team.domain.ro.TeamInviteRO;
import com.norm.timemall.app.team.domain.ro.TeamJoinedRO;
import com.norm.timemall.app.team.domain.ro.TeamOasisRO;
import com.norm.timemall.app.team.domain.vo.*;
import com.norm.timemall.app.team.service.TeamOasisJoinService;
import com.norm.timemall.app.team.service.TeamOasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
@Validated
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
    /**
     * 获取oasis announce info
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/oasis/announce/{oasis_id}")
    public TeamOasisAnnounceVO retrieveOasisAnnounce(@PathVariable("oasis_id") String oasisId){
        TeamOasisAnnounce announce = teamOasisService.findOasisAnnounce(oasisId);
        TeamOasisAnnounceVO vo = new TeamOasisAnnounceVO();
        vo.setAnnounce(announce);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    /**
     * 获取 Finance Value Index for Oasis
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/oasis_value_index")
    public TeamOasisValIndexVO retrieveOasisValIndex(@RequestParam @NotBlank(message = "oasisId is required")String oasisId){
        TeamOasisIndex index = teamOasisService.findOasisValIndex(oasisId);
        TeamOasisValIndexVO vo = new TeamOasisValIndexVO();
        vo.setIndex(index);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    /**
     * 获取已加入的oasis列表
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/joinedOases")
    public TeamJoinedVO retrieveJoinedOasis(String brandId){
        ArrayList<TeamJoinedRO> joinedRO = teamOasisJoinService.findJoinedOasis(brandId);
        TeamJoinedOasis joinedOases = new TeamJoinedOasis();
        joinedOases.setRecords(joinedRO);
        TeamJoinedVO vo = new TeamJoinedVO();
        vo.setJoined(joinedOases);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @ResponseBody
    @PutMapping(value = "/api/v1/team/acceptOasisInvitation")
    public SuccessVO joinAOasis(@RequestParam @NotBlank(message = "oasisId is required") String oasisId){
        teamOasisJoinService.acceptOasisInvitation(oasisId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/oasis/{oasis_id}/announce")
    public SuccessVO uploadOasisAnnounce(@PathVariable(value = "oasis_id") String oasisId,@RequestParam("file") MultipartFile file){
       // find oasis
        Oasis oasis = teamOasisService.findOneById(oasisId);
        if(oasis==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.OASIS_ANNOUNCE);
        // update oasis announce uri
        teamOasisService.modifyOasisAnnounce(oasisId,uri);
        // delete unused file
        fileStoreService.deleteFile(oasis.getAnnounce());

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/oasis/{oasis_id}/avatar")
    public SuccessVO uploadOasisAvatar(@PathVariable(value = "oasis_id") String oasisId,@RequestParam("file") MultipartFile file){
        // find oasis
        Oasis oasis = teamOasisService.findOneById(oasisId);
        if(oasis==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.OASIS_AVATAR);
        // update oasis announce uri
        teamOasisService.modifyOasisAvatar(oasisId,uri);
        // delete unused file
        fileStoreService.deleteFile(oasis.getAvatar());

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/risk")
    public SuccessVO modifyOasisRisk(@RequestBody TeamOasisRiskDTO dto){
        teamOasisService.modifyOasisRisk(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PostMapping(value = "/api/v1/team/oasis/new")
    public SuccessVO newOasis(@Validated  @RequestBody TeamNewOasisDTO dto){
        teamOasisService.newOasis(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/be_oasis_member")
    public SuccessVO followOasis(@RequestParam @NotBlank(message = "oasisId is required") String oasisId,
                                 @RequestParam @NotBlank(message = "brandId is required") String brandId){
        teamOasisJoinService.followOasis(oasisId,brandId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }


}
