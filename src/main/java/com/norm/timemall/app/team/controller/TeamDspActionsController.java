package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.service.TeamDspActionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamDspActionsController {
    @Autowired
    private TeamDspActionsService teamDspActionsService;

    @PutMapping("/api/v1/team/dsp_case/action/mps/paper/{id}/close")
    public SuccessVO closeCommercialPaper(@PathVariable("id") String id){

        teamDspActionsService.doCloseCommercialPaper(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
