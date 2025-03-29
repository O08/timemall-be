package com.norm.timemall.app.indicator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.IndDataLayerCellIndicesEventEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.indicator.domain.dto.CellIndicesPageDTO;
import com.norm.timemall.app.indicator.domain.dto.IndDataLayerCellIndicesDTO;
import com.norm.timemall.app.indicator.domain.ro.IndCellIndicesRO;
import com.norm.timemall.app.indicator.mapper.IndCellIndicesMapper;
import com.norm.timemall.app.indicator.service.CellIndicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CellIndicesServiceImpl implements CellIndicesService {
    @Autowired
    private IndCellIndicesMapper indCellIndicesMapper;
    @Override
    public IPage<IndCellIndicesRO> findCellIndices(CellIndicesPageDTO dto, CustomizeUser user) {
        IPage<IndCellIndicesRO>  page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        IPage<IndCellIndicesRO> cells = indCellIndicesMapper.selectCellIndicesPageByUserId(page, dto.getCode(),user.getUserId());
        return cells;
    }

    @Override
    public void modifyCellIndices(IndDataLayerCellIndicesDTO dto) {

        if(IndDataLayerCellIndicesEventEnum.IMPRESSIONS.getMark().equals(dto.getEvent())
                && CollUtil.isNotEmpty(dto.getCell().getImpressions())){
            indCellIndicesMapper.updateImpressions(dto);
        }
        if(IndDataLayerCellIndicesEventEnum.CLICKS.getMark().equals(dto.getEvent())
               && CollUtil.isNotEmpty(dto.getCell().getClicks())){
            indCellIndicesMapper.updateClicks(dto);
        }
        if(IndDataLayerCellIndicesEventEnum.APPOINTMENTS.getMark().equals(dto.getEvent())
               && CollUtil.isNotEmpty(dto.getCell().getAppointments())){
            indCellIndicesMapper.updateAppointments(dto);
        }
        if(IndDataLayerCellIndicesEventEnum.PURCHASES.getMark().equals(dto.getEvent())
                && ObjectUtil.isNotEmpty(dto.getCell().getPurchases())){
            indCellIndicesMapper.updatePurchase(dto);
        }

    }
}
