package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.dto.TeamOasisCollectAccountDTO;
import org.springframework.stereotype.Service;

@Service
public interface TeamOasisCollectAccountService {


    void collectAccountFromOasis(TeamOasisCollectAccountDTO dto);
}
