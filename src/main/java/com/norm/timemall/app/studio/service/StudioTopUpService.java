package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.base.pojo.ro.NewOrderRO;
import com.norm.timemall.app.studio.domain.dto.StudioTopUpDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface StudioTopUpService {
    NewOrderRO topUp(StudioTopUpDTO dto);

    void topUpPostHandler(String brandId, BigDecimal total);
}
