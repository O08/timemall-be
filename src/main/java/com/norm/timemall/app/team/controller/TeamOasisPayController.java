package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.TeamOasisCollectAccountDTO;
import com.norm.timemall.app.team.domain.dto.TeamTopUpOasisDTO;
import com.norm.timemall.app.team.service.TeamOasisCollectAccountService;
import com.norm.timemall.app.team.service.TeamOasisPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamOasisPayController {
    @Autowired
    private TeamOasisPayService teamOasisPayService;
    @Autowired
    private TeamOasisCollectAccountService teamOasisCollectAccountService;
    /**
     * oasis top up
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/team/top_up_to_oasis")
    public SuccessVO topUptoOasis(@Validated @RequestBody TeamTopUpOasisDTO dto){

        teamOasisPayService.topUptoOasis(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     * oasis collect account
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/team/oasis/collect_account")
    public SuccessVO collectAccountFromOasis( @Validated @RequestBody TeamOasisCollectAccountDTO dto){
        teamOasisCollectAccountService.collectAccountFromOasis(dto);

        return new SuccessVO(CodeEnum.SUCCESS);

    }
    

}
