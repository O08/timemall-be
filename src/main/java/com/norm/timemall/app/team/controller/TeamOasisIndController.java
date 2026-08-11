package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.service.TeamOasisIndService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class TeamOasisIndController {
    @Autowired
    private TeamOasisIndService teamOasisIndService;

    /**
     * 计算oasis指标
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/oasis/{oasis_id}/cal_index")
    public SuccessVO calIndex(@PathVariable("oasis_id") String oasisId){
        teamOasisIndService.calOasisIndex(oasisId);

        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
