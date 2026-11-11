package com.norm.timemall.app.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.mall.domain.dto.MallFetchPhysicalProductPageDTO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPhysicalProductPageRO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPhysicalProductInfoRO;
import org.springframework.stereotype.Service;

@Service
public interface MallPhysicalProductService {
    IPage<MallFetchPhysicalProductPageRO> findBrandProducts(MallFetchPhysicalProductPageDTO dto);

    MallFetchPhysicalProductInfoRO findProductProfile(String productId);
}
