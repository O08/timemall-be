package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.dto.StudioBrandBasicSetting;
import org.springframework.stereotype.Service;

@Service
public interface StudioBrandSubService {
    void modifyBrandSub(StudioBrandBasicSetting dto);
}
