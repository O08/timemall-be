package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubsProductPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioSubsProductChangeDTO;
import com.norm.timemall.app.studio.domain.dto.StudioSubsProductCreateDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsProductPageRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioSubscriptionProductService {
    void newProduct(StudioSubsProductCreateDTO dto);

    IPage<StudioGetSubsProductPageRO> findProducts(StudioGetSubsProductPageDTO dto);

    void modifyProduct(StudioSubsProductChangeDTO dto);

    void delProduct(String id);

}
