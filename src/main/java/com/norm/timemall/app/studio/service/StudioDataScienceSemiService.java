package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.FetchSemiDataPageDTO;
import com.norm.timemall.app.studio.domain.ro.FetchSemiDataRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioDataScienceSemiService {
    IPage<FetchSemiDataRO> findSemiDataPage(FetchSemiDataPageDTO dto);
}
