package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.pojo.TeamTrans;
import com.norm.timemall.app.team.domain.vo.TeamTransVO;
import com.norm.timemall.app.team.service.TeamTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamTransController {
    @Autowired
    private TeamTransService teamTransService;
    /**
     * 获取交易数据
     * fid 交易对象
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/trans/{fid}")
    public TeamTransVO retrieveTrans(@PathVariable(value = "fid") String fid, @RequestParam("year") String year,
                                     @RequestParam("month") String month){
        TeamTrans trans = teamTransService.findTrans(fid,year,month);
        TeamTransVO vo = new TeamTransVO();
        vo.setTrans(trans);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
}
