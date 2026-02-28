package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.dto.TeamTopUpOasisDTO;
import org.springframework.stereotype.Service;

@Service
public interface TeamOasisPayService {
    void topUptoOasis(TeamTopUpOasisDTO dto);
}
