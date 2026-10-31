package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.PhysicalProduct;
import com.norm.timemall.app.studio.domain.dto.StudioChangePhysicalProductStatusDTO;
import com.norm.timemall.app.studio.domain.dto.StudioCreatePhysicalProductDTO;
import com.norm.timemall.app.studio.domain.dto.StudioEditPhysicalProductDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchPhysicalProductPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalProductPageRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioPhysicalProductService {

    void newProduct(StudioCreatePhysicalProductDTO dto, String thumbnailUrl);

    IPage<StudioFetchPhysicalProductPageRO> fetchProductPage(StudioFetchPhysicalProductPageDTO dto);

    PhysicalProduct findOneProduct(String productId);

    void editProductInfo(StudioEditPhysicalProductDTO dto);

    String removeProduct(String productId);

    void changeProductStatus(StudioChangePhysicalProductStatusDTO dto);
}
