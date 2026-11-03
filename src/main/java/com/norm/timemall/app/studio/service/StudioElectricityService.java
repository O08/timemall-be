package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.ro.StudioGetElectricityProductInfoRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioElectricityService {
    StudioGetElectricityProductInfoRO findProductInfo();


    void buyElectricity();


}
