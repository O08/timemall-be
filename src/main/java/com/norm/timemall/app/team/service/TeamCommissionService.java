package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.team.domain.dto.TeamAcceptOasisTaskDTO;
import com.norm.timemall.app.team.domain.dto.TeamCommissionDTO;
import com.norm.timemall.app.team.domain.dto.TeamFinishOasisTask;
import com.norm.timemall.app.team.domain.dto.TeamOasisNewTaskDTO;
import com.norm.timemall.app.team.domain.ro.TeamCommissionRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamCommissionService {
    IPage<TeamCommissionRO> findCommission(TeamCommissionDTO dto);

    void newOasisTask(TeamOasisNewTaskDTO dto);

    void acceptOasisTask(TeamAcceptOasisTaskDTO dto);

    void finishOasisTask(TeamFinishOasisTask dto);
}
