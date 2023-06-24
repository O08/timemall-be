package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.StudioMpsChainPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioNewMpsChainDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsChainDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchAChain;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsChainRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioMpsChainService {
    IPage<StudioFetchMpsChainRO> fetchMpsChainPage(StudioMpsChainPageDTO dto);

    String  newMpsChain(StudioNewMpsChainDTO dto);

    void modifyMpsChain(StudioPutMpsChainDTO dto);

    StudioFetchAChain findChainInfo(String chainId);
}
