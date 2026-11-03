package com.norm.timemall.app.pod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.pod.domain.dto.PodFetchSponsorshipPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchSponsorshipPageRO;
import org.springframework.stereotype.Service;

@Service
public interface PodSponsorshipService {

    /**
     * 当前品牌收到的赞助记录分页（sponsoree_brand_id = currentBrandId）
     */
    IPage<PodFetchSponsorshipPageRO> fetchSponsorshipPage(PodFetchSponsorshipPageDTO dto);
}
