package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.TeamTalentPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamTalentRO;
import com.norm.timemall.app.team.domain.vo.TeamTalentPageVO;
import com.norm.timemall.app.team.service.TeamBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamBrandController {

    @Autowired
    private TeamBrandService teamBrandService;
    /**
     * 商家黄页
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/talent")
    public TeamTalentPageVO retrieveTalents(@Validated TeamTalentPageDTO dto){
        IPage<TeamTalentRO>  talent = teamBrandService.findTalents(dto);
        TeamTalentPageVO vo = new TeamTalentPageVO();
        vo.setTalent(talent);
        vo.setResponseCode(CodeEnum.SUCCESS);

       return vo;
    }
}
