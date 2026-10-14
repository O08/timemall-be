package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.ro.FetchBluvarrierRO;
import org.springframework.stereotype.Service;

@Service
public interface BluvarrierService {
    FetchBluvarrierRO findBluvarrier();


}
