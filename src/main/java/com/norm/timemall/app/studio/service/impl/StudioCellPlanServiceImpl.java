package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.enums.CellPlanTypeEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.CellPlan;
import com.norm.timemall.app.studio.domain.dto.StudioPutCellPlanDTO;
import com.norm.timemall.app.studio.mapper.StudioCellPlanMapper;
import com.norm.timemall.app.studio.service.StudioCellPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudioCellPlanServiceImpl implements StudioCellPlanService {
    @Autowired
    private StudioCellPlanMapper studioCellPlanMapper;
    @Override
    public void configCellPlan(StudioPutCellPlanDTO dto) {
        // validate planType
        Collector<CellPlanTypeEnum, ?, Map<String, CellPlanTypeEnum>> cellPlanTypeEnumMapCollector = Collectors.toMap(CellPlanTypeEnum::getMark, Function.identity());
        Map<String, CellPlanTypeEnum> cellPlanTypeEnumMap  = Stream.of(CellPlanTypeEnum.values())
                .collect(cellPlanTypeEnumMapCollector);

        if(ObjectUtil.isEmpty(cellPlanTypeEnumMap.get(dto.getPlanType()))){
            throw  new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        if(StrUtil.isEmpty(dto.getPlanId())){
            doAddNewCellPlan(dto,cellPlanTypeEnumMap);
        }
        if(!StrUtil.isEmpty(dto.getPlanId())){
            doModifyCellPlan(dto,cellPlanTypeEnumMap);
        }
    }
    private void doAddNewCellPlan(StudioPutCellPlanDTO dto,Map<String, CellPlanTypeEnum> cellPlanTypeEnumMap ){
        CellPlan plan = new CellPlan();
        plan.setId(IdUtil.simpleUUID())
                .setCellId(dto.getCellId())
                .setPlanType(dto.getPlanType())
                .setPlanTypeDesc(cellPlanTypeEnumMap.get(dto.getPlanType()).getDesc())
                .setTitle(dto.getTitle())
                .setFeature(dto.getFeature())
                .setPrice(dto.getPrice())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioCellPlanMapper.insert(plan);
    }
    private void doModifyCellPlan(StudioPutCellPlanDTO dto,Map<String, CellPlanTypeEnum> cellPlanTypeEnumMap ){
        CellPlan plan = new CellPlan();
        plan.setId(dto.getPlanId())
                .setCellId(dto.getCellId())
                .setPlanType(dto.getPlanType())
                .setPlanTypeDesc(cellPlanTypeEnumMap.get(dto.getPlanType()).getDesc())
                .setTitle(dto.getTitle())
                .setFeature(dto.getFeature())
                .setPrice(dto.getPrice())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioCellPlanMapper.updateById(plan);
    }

}
