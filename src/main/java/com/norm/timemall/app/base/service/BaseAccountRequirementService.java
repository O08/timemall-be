package com.norm.timemall.app.base.service;

import org.springframework.stereotype.Service;

@Service
public interface BaseAccountRequirementService {
    void callGenRequirementFunId(String userId, String brandId);

    boolean checkRequirement();

}
