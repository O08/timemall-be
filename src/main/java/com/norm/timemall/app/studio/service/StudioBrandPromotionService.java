package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.dto.StudioRefreshPromotionDTO;
import com.norm.timemall.app.studio.domain.ro.FetchBrandPromotionRO;
import com.norm.timemall.app.studio.domain.ro.FetchCellCouponBenefitRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioBrandPromotionService {
    FetchBrandPromotionRO findBrandPromotion();

    void setupBrandPromotionInfo();

    void modifyPromotion(StudioRefreshPromotionDTO dto);

    FetchCellCouponBenefitRO findCouponBenefit(String cellId,String supplierBrandId);
}
