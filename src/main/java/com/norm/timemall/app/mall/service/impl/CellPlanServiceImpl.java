package com.norm.timemall.app.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.mall.domain.dto.RetrievePlanPageDTO;
import com.norm.timemall.app.mall.domain.pojo.FetchCellPlan;
import com.norm.timemall.app.mall.domain.ro.CellRO;
import com.norm.timemall.app.mall.domain.ro.FetchCellPlanRO;
import com.norm.timemall.app.mall.domain.ro.RetrievePlanRO;
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

    @Override
    public IPage<RetrievePlanRO> findCellPlans(RetrievePlanPageDTO dto) {

        Page<CellRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return cellPlanMapper.selectCellPlanPage(page, dto);

    }
}
