package com.norm.timemall.app.affiliate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchApiMarketingPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchApiMarketingPageRO;
import com.norm.timemall.app.affiliate.domain.vo.FetchApiMarketingPageVO;
import com.norm.timemall.app.affiliate.service.AffiliateApiMarketingService;
import com.norm.timemall.app.base.enums.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIMarketingController {
    @Autowired
    private AffiliateApiMarketingService affiliateApiMarketingService;
    @GetMapping("/api/v1/web/affiliate/outreach_api_ind")
    public FetchApiMarketingPageVO fetchApiMarketingPage(FetchApiMarketingPageDTO dto){

        IPage<FetchApiMarketingPageRO> ind = affiliateApiMarketingService.findApiMarketingRecord(dto);
        FetchApiMarketingPageVO vo = new FetchApiMarketingPageVO();
        vo.setInd(ind);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
