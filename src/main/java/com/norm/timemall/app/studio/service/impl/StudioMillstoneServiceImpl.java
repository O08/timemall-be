package com.norm.timemall.app.studio.service.impl;

import com.norm.timemall.app.studio.mapper.StudioMillstoneMapper;
import com.norm.timemall.app.studio.service.StudioMillstoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioMillstoneServiceImpl implements StudioMillstoneService {
    @Autowired
    private StudioMillstoneMapper studioMillstoneMapper;


    @Override
    public void markWorkFlowsForBrandByIdAndCode(String workflowId, String code) {
        studioMillstoneMapper.updateWorkflowForBrandByIdAndCode(workflowId,code);
    }
}
