package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Commission;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.pojo.TeamFetchCommissionDetail;
import com.norm.timemall.app.team.domain.ro.TeamCommissionRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamCommissionService {
    IPage<TeamCommissionRO> findCommission(TeamCommissionDTO dto);

    void newOasisTask(TeamOasisNewTaskDTO dto);

    void acceptOasisTask(TeamAcceptOasisTaskDTO dto);

    void finishOasisTask(TeamFinishOasisTaskDTO dto);

    TeamFetchCommissionDetail findCommissionDetail(String id);

    Commission findCommissionUsingId(String commissionId);

    void examineOasisTask(TeamExamineOasisTaskDTO dto);
}
