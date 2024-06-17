package com.norm.timemall.app.affiliate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.DelLinkDTO;
import com.norm.timemall.app.affiliate.domain.dto.FetchLinkMarketingPageDTO;
import com.norm.timemall.app.affiliate.domain.dto.NewLinkMarketingDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchLinkMarketingPageRO;
import com.norm.timemall.app.affiliate.domain.vo.FetchLinkMarketingPageVO;
import com.norm.timemall.app.affiliate.service.AffiliateLinkMarketingService;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class LinkMarketingController {
    @Autowired
    private AffiliateLinkMarketingService affiliateLinkMarketingService;
    @DeleteMapping("/api/v1/web/affiliate/del_link_marketing")
    public SuccessVO delLink(@RequestBody DelLinkDTO dto){
        affiliateLinkMarketingService.deleteLinkRecord(dto.getLinkMarketingId());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/web/affiliate/outreach_link_ind")
    public FetchLinkMarketingPageVO fetchLinkMarketing(FetchLinkMarketingPageDTO dto){

        IPage<FetchLinkMarketingPageRO> ind=affiliateLinkMarketingService.findLinkMarketingRecord(dto);
        FetchLinkMarketingPageVO vo= new FetchLinkMarketingPageVO();
        vo.setInd(ind);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @PostMapping("/api/v1/web/affiliate/new_product_marketing")
    public SuccessVO newLinkMarketing(@RequestBody NewLinkMarketingDTO dto){
        affiliateLinkMarketingService.addOneLinkMarketingRecord(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @GetMapping(value = "/api/v1/web_mall/affiliate/short_link/{shortUrl}")
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrl) {
        String url = affiliateLinkMarketingService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
