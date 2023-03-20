package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.pojo.TeamTrans;
import com.norm.timemall.app.team.domain.vo.TeamTransVO;
import com.norm.timemall.app.team.service.TeamTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Validated
@RestController
public class TeamTransController {
    @Autowired
    private TeamTransService teamTransService;
    /**
     * 获取交易数据
     * fid 交易对象
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/trans")
    public TeamTransVO retrieveTrans(@RequestParam @NotBlank(message = "fid is required") String fid,
                                     @RequestParam @NotBlank(message = "fidType is required") String fidType,
                                     @RequestParam @NotBlank(message = "year is required") String year,
                                     @RequestParam @NotBlank(message = "month is required") String month){
        TeamTrans trans = teamTransService.findTrans(fid,fidType,year,month);
        TeamTransVO vo = new TeamTransVO();
        vo.setTrans(trans);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
}
