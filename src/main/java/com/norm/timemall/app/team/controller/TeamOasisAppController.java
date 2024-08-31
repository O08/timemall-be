package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.GetAppDTO;
import com.norm.timemall.app.team.domain.ro.FetchOasisAppListRO;
import com.norm.timemall.app.team.domain.vo.FetchOasisAppListVO;
import com.norm.timemall.app.team.service.TeamMiniAppLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class TeamOasisAppController {

    @Autowired
    private TeamMiniAppLibraryService teamMiniAppLibraryService;
    @ResponseBody
    @GetMapping(value = "/api/v1/team/oasis/app/list")
    public FetchOasisAppListVO fetchOasisAppList(){

        ArrayList<FetchOasisAppListRO> ros= teamMiniAppLibraryService.findAppList();
        FetchOasisAppListVO vo = new FetchOasisAppListVO();
        vo.setApp(ros);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @ResponseBody
    @PostMapping("/api/v1/team/oasis/app/get")
    public SuccessVO getApp(@RequestBody @Validated GetAppDTO dto){

        teamMiniAppLibraryService.installAppToOasis(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
