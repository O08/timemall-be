package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.TeamObjPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamSwapCellDTO;
import com.norm.timemall.app.team.domain.ro.TeamObjRO;
import com.norm.timemall.app.team.domain.vo.TeamObjPageVO;
import com.norm.timemall.app.team.service.TeamObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamObjController {
    @Autowired
    private TeamObjService teamObjService;
    /**
     * 获取obj列表
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/obj")
    public TeamObjPageVO retrieveObj(@Validated TeamObjPageDTO dto){
        IPage<TeamObjRO> objRos = teamObjService.findObjs(dto);
        TeamObjPageVO vo = new TeamObjPageVO();
        vo.setObj(objRos);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/swap_cell")
    public SuccessVO swapCell(TeamSwapCellDTO dto){
        teamObjService.swapCell(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
