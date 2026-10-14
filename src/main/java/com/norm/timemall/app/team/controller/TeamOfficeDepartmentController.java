package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.TeamOfficeChangeDepartmentDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeNewDepartmentDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeQueryDepartmentPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryDepartmentPageRO;
import com.norm.timemall.app.team.domain.vo.TeamOfficeQueryDepartmentPageVO;
import com.norm.timemall.app.team.service.TeamOfficeDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamOfficeDepartmentController {

    @Autowired
    private TeamOfficeDepartmentService teamOfficeDepartmentService;

    @GetMapping("/api/v1/team/office/department/query")
    public TeamOfficeQueryDepartmentPageVO queryDepartment(@Validated TeamOfficeQueryDepartmentPageDTO dto){
        IPage<TeamOfficeQueryDepartmentPageRO> department = teamOfficeDepartmentService.findDepartments(dto);
        TeamOfficeQueryDepartmentPageVO vo = new TeamOfficeQueryDepartmentPageVO();
        vo.setDepartment(department);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }

    @PostMapping("/api/v1/team/office/department/create")
    public SuccessVO createOneDepartment(@Validated @RequestBody TeamOfficeNewDepartmentDTO dto){
        teamOfficeDepartmentService.createDepartment(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/team/office/department/edit")
    public SuccessVO changeDepartment(@Validated @RequestBody TeamOfficeChangeDepartmentDTO dto ){
        teamOfficeDepartmentService.editDepartment(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @DeleteMapping("/api/v1/team/office/department/{id}/del")
    public SuccessVO removeDepartment(@PathVariable("id") String id){
        teamOfficeDepartmentService.delOneDepartment(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
