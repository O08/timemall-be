package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.dto.StudioPutCellPlanDTO;
import org.springframework.stereotype.Service;

@Service
public interface StudioCellPlanService {
    void configCellPlan(StudioPutCellPlanDTO dto);
}
