package com.norm.timemall.app.pod.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.pod.domain.dto.PodFetchSponsorshipPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchSponsorshipPageRO;
import com.norm.timemall.app.pod.domain.vo.PodFetchSponsorshipPageVO;
import com.norm.timemall.app.pod.service.PodSponsorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PodSponsorshipController {
    @Autowired
    private PodSponsorshipService podSponsorshipService;

    @GetMapping("/api/v1/web_pod/brand/sponsorship")
    public PodFetchSponsorshipPageVO sponsorship(@Validated PodFetchSponsorshipPageDTO dto) {
        IPage<PodFetchSponsorshipPageRO> page = podSponsorshipService.fetchSponsorshipPage(dto);
        PodFetchSponsorshipPageVO vo = new PodFetchSponsorshipPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setSponsorship(page);
        return vo;
    }
}
