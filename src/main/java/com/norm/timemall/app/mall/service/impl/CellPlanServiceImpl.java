package com.norm.timemall.app.mall.service.impl;

import com.norm.timemall.app.mall.domain.pojo.FetchCellPlan;
import com.norm.timemall.app.mall.domain.ro.FetchCellPlanRO;
import com.norm.timemall.app.mall.mapper.CellPlanMapper;
import com.norm.timemall.app.mall.service.CellPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CellPlanServiceImpl implements CellPlanService {
    @Autowired
    private CellPlanMapper cellPlanMapper;
    @Override
    public FetchCellPlan findCellPlan(String cellId) {

        ArrayList<FetchCellPlanRO> records= cellPlanMapper.selectCellPlanByCellId(cellId);
        FetchCellPlan plan = new FetchCellPlan();
        plan.setRecords(records);
        return plan;

    }
}
