package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.pojo.TeamTrans;
import com.norm.timemall.app.team.domain.vo.TeamTransPageVO;
import com.norm.timemall.app.team.service.TeamTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public TeamTransPageVO retrieveTrans(@Validated PageDTO dto){
        IPage<TeamTrans> trans = teamTransService.findTrans(dto);
        TeamTransPageVO vo = new TeamTransPageVO();
        vo.setTrans(trans);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
}
