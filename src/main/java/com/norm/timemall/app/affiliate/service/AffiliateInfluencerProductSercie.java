package com.norm.timemall.app.affiliate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchInfluencerProductPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchInfluencerProductRO;
import org.springframework.stereotype.Service;

@Service
public interface AffiliateInfluencerProductSercie {
    void newChoice(String cellId);

    IPage<FetchInfluencerProductRO> findChoiceRecord(FetchInfluencerProductPageDTO dto);

    void delChoiceRecord(String cellId);
}
