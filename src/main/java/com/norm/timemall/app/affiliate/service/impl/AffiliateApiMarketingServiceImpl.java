package com.norm.timemall.app.affiliate.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.affiliate.domain.dto.FetchApiMarketingPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchApiMarketingPageRO;
import com.norm.timemall.app.affiliate.mapper.AffiliateApiMarketingMapper;
import com.norm.timemall.app.affiliate.mapper.AffiliateLinkMarketingMapper;
import com.norm.timemall.app.affiliate.service.AffiliateApiMarketingService;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AffiliateApiMarketingServiceImpl implements AffiliateApiMarketingService {

    @Autowired
    private AffiliateApiMarketingMapper affiliateApiMarketingMapper;
    @Override
    public IPage<FetchApiMarketingPageRO> findApiMarketingRecord(FetchApiMarketingPageDTO dto) {

        IPage<FetchApiMarketingPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return affiliateApiMarketingMapper.selectPageByDTO(page, SecurityUserHelper.getCurrentPrincipal().getBrandId(), dto);

    }
}
