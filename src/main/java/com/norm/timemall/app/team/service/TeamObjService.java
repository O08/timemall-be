package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.team.domain.dto.TeamObjPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamOwnedObjPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamSwapCellDTO;
import com.norm.timemall.app.team.domain.dto.TeamTagObjDTO;
import com.norm.timemall.app.team.domain.ro.TeamObjRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamObjService {
    IPage<TeamObjRO> findObjs(TeamObjPageDTO dto);

    void swapCell(TeamSwapCellDTO dto);

    IPage<TeamObjRO> findOwnedObjs(TeamOwnedObjPageDTO dto);

    void tagObj(TeamTagObjDTO dto);
}
