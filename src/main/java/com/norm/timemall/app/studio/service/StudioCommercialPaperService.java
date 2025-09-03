package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaper;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaperDetail;
import com.norm.timemall.app.studio.domain.ro.StudioDiscoverMpsPaperPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsPaperRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioCommercialPaperService {
    void generateMpsPaper(String chainId,String brandId,String mpsId);

    IPage<StudioFetchMpsPaperRO> findPaperPageForBrand(StudioFetchMpsPaperPageDTO dto);

    IPage<StudioDiscoverMpsPaperPageRO> discoverMpsPaper(StudioDiscoverMpsPaperPageDTO dto);

    StudioFetchMpsPaperDetail findMpsPaperDetail(String id);

    StudioFetchMpsPaper findPaperList(StudioFetchmpsPaperListDTO dto);

    void modifyPaperTag(StudioPutMpsPaperTagDTO dto);

    void modifyPaper(StudioPutMpsPaperDTO dto);

    void mpsPaperOrderReceiving(StudioMpsOrderReceivingDTO dto);

    void modifyPapersTag(String mpsId, String mark);

    void modifyPaperTagForCurrentUser(String paperId, String mark);

    void generateFastMpsPaper(StudioNewFastMpsDTO dto, String brandId, String mpsId);

    void emptySupplier(String id);


}
