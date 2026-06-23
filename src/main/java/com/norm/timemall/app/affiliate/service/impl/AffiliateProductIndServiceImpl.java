package com.norm.timemall.app.affiliate.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.affiliate.domain.dto.FetchProductGalleryPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchProductGalleryRO;
import com.norm.timemall.app.affiliate.mapper.AffiliateProductIndMapper;
import com.norm.timemall.app.affiliate.service.AffiliateProductIndService;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AffiliateProductIndServiceImpl implements AffiliateProductIndService {

    @Autowired
    private AffiliateProductIndMapper affiliateProductIndMapper;
    @Override
    public IPage<FetchProductGalleryRO> findProductGallery(FetchProductGalleryPageDTO dto) {
        IPage<FetchProductGalleryRO> page  = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        dto.setBrandId(brandId);
        return affiliateProductIndMapper.selectPageByDTO(page,dto);
    }
}
