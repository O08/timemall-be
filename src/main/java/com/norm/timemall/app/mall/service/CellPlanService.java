package com.norm.timemall.app.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.mall.domain.dto.RetrievePlanPageDTO;
import com.norm.timemall.app.mall.domain.pojo.FetchCellPlan;
import com.norm.timemall.app.mall.domain.ro.RetrievePlanRO;
import org.springframework.stereotype.Service;

@Service
public interface CellPlanService {
    FetchCellPlan findCellPlan(String cellId);

    IPage<RetrievePlanRO> findCellPlans(RetrievePlanPageDTO dto);
}
