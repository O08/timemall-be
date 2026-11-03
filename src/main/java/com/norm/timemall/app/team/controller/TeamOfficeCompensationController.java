package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.TeamOfficeChangeCompensationDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeCreateCompensationDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeQueryCompensationPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryCompensationPageRO;
import com.norm.timemall.app.team.domain.vo.TeamOfficeQueryCompensationPageVO;
import com.norm.timemall.app.team.service.TeamOfficeCompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamOfficeCompensationController {
    @Autowired
    private TeamOfficeCompensationService teamOfficeCompensationService;

    @GetMapping("/api/v1/team/office/compensation/query")
    public TeamOfficeQueryCompensationPageVO queryCompensations(@Validated TeamOfficeQueryCompensationPageDTO dto){
        IPage<TeamOfficeQueryCompensationPageRO> compensation =teamOfficeCompensationService.findCompensations(dto) ;
        TeamOfficeQueryCompensationPageVO vo = new TeamOfficeQueryCompensationPageVO();
        vo.setCompensation(compensation);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @PostMapping("/api/v1/team/office/compensation/create")
    public SuccessVO createCompensation(@Validated @RequestBody TeamOfficeCreateCompensationDTO dto){
        teamOfficeCompensationService.addOneCompensation(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/team/office/compensation/edit")
    public SuccessVO changeCompensation(@Validated @RequestBody TeamOfficeChangeCompensationDTO dto){
        teamOfficeCompensationService.editCompensation(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @DeleteMapping("/api/v1/team/office/compensation/{id}/del")
    public SuccessVO removeCompensation(@PathVariable("id") String id){
        teamOfficeCompensationService.delOneCompensation(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
