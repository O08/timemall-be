package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.pojo.FetchUserInterests;
import org.springframework.stereotype.Service;

@Service
public interface CustomerInterestService {
    FetchUserInterests findInterest();

}
