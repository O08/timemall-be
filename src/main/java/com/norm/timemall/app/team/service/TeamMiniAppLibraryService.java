package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.dto.GetAppDTO;
import com.norm.timemall.app.team.domain.ro.FetchOasisAppListRO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface TeamMiniAppLibraryService {
    ArrayList<FetchOasisAppListRO> findAppList();

    void installAppToOasis(GetAppDTO dto);
}
