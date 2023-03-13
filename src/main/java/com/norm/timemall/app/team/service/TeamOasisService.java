package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.team.domain.dto.TeamNewOasisDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisRiskDTO;
import com.norm.timemall.app.team.domain.ro.TeamOasisRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamOasisService {
    IPage<TeamOasisRO> findOasis(TeamOasisPageDTO pageDTO);

    Oasis findOneById(String oasisId);

    void modifyOasisAnnounce(String oasisId, String uri);

    void modifyOasisRisk(TeamOasisRiskDTO dto);

    void newOasis(TeamNewOasisDTO dto);
}
