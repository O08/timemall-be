package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.BrandMarkEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.mo.OasisMember;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.team.domain.dto.TeamOasisChangeManagerDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisGeneralDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisRiskDTO;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import com.norm.timemall.app.team.service.TeamOasisMemberService;
import com.norm.timemall.app.team.service.TeamOasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;

@Validated
@RestController
public class TeamSettingOasisController {
    @Autowired
    private TeamOasisService teamOasisService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;

    @Autowired
    private TeamOasisMemberService teamOasisMemberService;

    @Autowired
    private AccountService accountService;
    @ResponseBody
    @PutMapping(value = "/api/v1/team/oasis/mark")
    public SuccessVO modifyOasisRisk(@RequestParam
                                         @NotBlank(message = "oasisId is required") String oasisId,
                                     @RequestParam
                                     @NotBlank(message = "mark is required") String mark){
        teamOasisService.tagOasisTag(oasisId,mark);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/oasis/general")
    public SuccessVO modifyOasisBaseInfo(@Validated @RequestBody TeamOasisGeneralDTO dto){
        teamOasisService.modifyOasisBaseInfo(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/team/dsp_case/action/oasis/{oasis_id}/freeze")
    public SuccessVO freezeOasis(@PathVariable("oasis_id") String oasisId){

        teamOasisService.blockedOasis(oasisId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/team/oasis/manager/change")
    public SuccessVO changeManager(@Validated @RequestBody TeamOasisChangeManagerDTO dto){

        // check user
        boolean passed = teamDataPolicyService.passIfBrandIsCreatorOfOasis(dto.getOasisId());
        if(!passed){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // check oasis member
        OasisMember memberInfo = teamOasisMemberService.findOneMember(dto.getOasisId(), dto.getNewManagerBrandId());
        if(memberInfo==null){
            throw new QuickMessageException("新管理员不是部落成员，移交失败");
        }

        // check new manager name
        Brand managerInfo = accountService.findBrandInfoByBrandId(dto.getNewManagerBrandId());
        if(managerInfo==null){
            throw new QuickMessageException("manager not exist.");
        }
        if(!dto.getNewManagerName().equals(managerInfo.getRealName())){
            throw new QuickMessageException("新管理员名称校验不通过,请联系对方获取");
        }
        if(BrandMarkEnum.CLOSED.getMark().equals(managerInfo.getMark())){
            throw new QuickMessageException("新管理员已删除账号，移交失败");
        }
        String oldManagerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(oldManagerBrandId.equals(managerInfo.getId())){
            throw new QuickMessageException("新管理员与原管理员相同，移交失败");
        }
        teamOasisService.changeOasisManager(dto);

        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
