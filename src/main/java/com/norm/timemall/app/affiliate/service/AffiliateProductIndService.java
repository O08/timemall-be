package com.norm.timemall.app.affiliate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchProductGalleryPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchProductGalleryRO;
import org.springframework.stereotype.Service;

@Service
public interface AffiliateProductIndService {
    IPage<FetchProductGalleryRO> findProductGallery(FetchProductGalleryPageDTO dto);
}
