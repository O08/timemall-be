package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.TeamOasisGeneralDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisRiskDTO;
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
}
