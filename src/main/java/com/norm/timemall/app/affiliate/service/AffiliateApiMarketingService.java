package com.norm.timemall.app.affiliate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchApiMarketingPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchApiMarketingPageRO;
import org.springframework.stereotype.Service;

@Service
public interface AffiliateApiMarketingService {
    IPage<FetchApiMarketingPageRO> findApiMarketingRecord(FetchApiMarketingPageDTO dto);
}
