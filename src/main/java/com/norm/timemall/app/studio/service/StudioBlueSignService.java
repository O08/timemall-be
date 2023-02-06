package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.pojo.StudioBlueSign;
import org.springframework.stereotype.Service;

@Service
public interface StudioBlueSignService {
    StudioBlueSign findStudioBlueSign(String brandId);
}
