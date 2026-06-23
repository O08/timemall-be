package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.dto.MallFetchPromotionInfoDTO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPromotionBenefitRO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPromotionInfoRO;
import org.springframework.stereotype.Service;

@Service
public interface MallBrandPromotionService {
    MallFetchPromotionInfoRO findPromotionInfo(MallFetchPromotionInfoDTO dto);
    MallFetchPromotionBenefitRO findPromotionBenefit(String cellId, String supplierBrandId);
}
