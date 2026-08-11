package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.team.domain.dto.TeamOfficeChangeDepartmentDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeNewDepartmentDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeQueryDepartmentPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryDepartmentPageRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamOfficeDepartmentService {
    IPage<TeamOfficeQueryDepartmentPageRO> findDepartments(TeamOfficeQueryDepartmentPageDTO dto);

    void createDepartment(TeamOfficeNewDepartmentDTO dto);

    void editDepartment(TeamOfficeChangeDepartmentDTO dto);

    void delOneDepartment(String id);
}
