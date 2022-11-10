package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.dto.StudioPricingDTO;
import org.springframework.stereotype.Service;

@Service
public interface StudioPricingService {
    void newPricing(String cellId, StudioPricingDTO dto);

}
