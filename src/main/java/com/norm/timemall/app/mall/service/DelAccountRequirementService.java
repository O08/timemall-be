package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.pojo.FetchDelAccountRequirement;
import org.springframework.stereotype.Service;

@Service
public interface DelAccountRequirementService {
    FetchDelAccountRequirement findRequirement();

}
