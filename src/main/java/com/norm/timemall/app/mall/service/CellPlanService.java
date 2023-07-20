package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.pojo.FetchCellPlan;
import org.springframework.stereotype.Service;

@Service
public interface CellPlanService {
    FetchCellPlan findCellPlan(String cellId);
}
