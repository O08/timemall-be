package com.norm.timemall.app.affiliate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchLinkMarketingPageDTO;
import com.norm.timemall.app.affiliate.domain.dto.NewLinkMarketingDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchLinkMarketingPageRO;
import org.springframework.stereotype.Service;

@Service
public interface AffiliateLinkMarketingService {
    void deleteLinkRecord(String linkMarketingId);

    IPage<FetchLinkMarketingPageRO> findLinkMarketingRecord(FetchLinkMarketingPageDTO dto);

    void addOneLinkMarketingRecord(NewLinkMarketingDTO dto);

    String getOriginalUrl(String shortUrl);
}
