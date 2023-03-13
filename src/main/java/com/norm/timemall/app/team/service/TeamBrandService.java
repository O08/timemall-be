package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.team.domain.dto.TeamTalentPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamTalentRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamBrandService {
    IPage<TeamTalentRO> findTalents(TeamTalentPageDTO dto);
}
