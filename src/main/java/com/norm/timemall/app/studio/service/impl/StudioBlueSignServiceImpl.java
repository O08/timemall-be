package com.norm.timemall.app.studio.service.impl;

import com.norm.timemall.app.studio.domain.pojo.StudioBlueSign;
import com.norm.timemall.app.studio.mapper.StudioProprietaryTradingMapper;
import com.norm.timemall.app.studio.service.StudioBlueSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioBlueSignServiceImpl implements StudioBlueSignService {
    @Autowired
    private StudioProprietaryTradingMapper tradingMapper;
    @Override
    public StudioBlueSign findStudioBlueSign(String brandId) {
        return tradingMapper.selectBlueSign(brandId);
    }
}
