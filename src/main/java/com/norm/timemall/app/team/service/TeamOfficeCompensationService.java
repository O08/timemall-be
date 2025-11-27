package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.team.domain.dto.TeamOfficeChangeCompensationDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeCreateCompensationDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeQueryCompensationPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryCompensationPageRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamOfficeCompensationService {
    IPage<TeamOfficeQueryCompensationPageRO> findCompensations(TeamOfficeQueryCompensationPageDTO dto);

    void addOneCompensation(TeamOfficeCreateCompensationDTO dto);

    void editCompensation(TeamOfficeChangeCompensationDTO dto);

    void delOneCompensation(String id);
}
