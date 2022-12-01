package com.norm.timemall.app.studio.service;

import org.springframework.stereotype.Service;

@Service
public interface StudioMillstoneService {
    void markWorkFlowsForBrandByIdAndCode(String workflwoId, String code);

}
