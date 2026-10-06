package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Mps;
import com.norm.timemall.app.studio.domain.dto.StudioFetchMpsListPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioNewFastMpsDTO;
import com.norm.timemall.app.studio.domain.dto.StudioNewMpsDTO;
import com.norm.timemall.app.studio.domain.dto.StudioTaggingMpsDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsListRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioMpsService {
    IPage<StudioFetchMpsListRO> fetchMpsList(StudioFetchMpsListPageDTO dto);

    Mps newMps(StudioNewMpsDTO dto);

    void taggingMps(StudioTaggingMpsDTO dto);

    Mps findMps(String mpsId);

    Mps newFastMps(StudioNewFastMpsDTO dto);
}
