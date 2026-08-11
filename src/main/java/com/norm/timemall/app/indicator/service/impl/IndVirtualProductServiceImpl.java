package com.norm.timemall.app.indicator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.norm.timemall.app.base.enums.IndDataLayerIndicesEventEnum;
import com.norm.timemall.app.indicator.domain.dto.IndDataLayerVirtualProductIndicesDTO;
import com.norm.timemall.app.indicator.mapper.IndVirtualProductMapper;
import com.norm.timemall.app.indicator.service.IndVirtualProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndVirtualProductServiceImpl implements IndVirtualProductService {

    @Autowired
    private IndVirtualProductMapper indVirtualProductMapper;
    @Override
    public void modifyIndices(IndDataLayerVirtualProductIndicesDTO dto) {
        if(IndDataLayerIndicesEventEnum.IMPRESSIONS.getMark().equals(dto.getEvent())
                && CollUtil.isNotEmpty(dto.getVirtual().getImpressions())){
            indVirtualProductMapper.updateImpressions(dto);
        }
        if(IndDataLayerIndicesEventEnum.CLICKS.getMark().equals(dto.getEvent())
                && CollUtil.isNotEmpty(dto.getVirtual().getClicks())){
            indVirtualProductMapper.updateClicks(dto);
        }
        if(IndDataLayerIndicesEventEnum.PURCHASES.getMark().equals(dto.getEvent())
                && ObjectUtil.isNotEmpty(dto.getVirtual().getPurchases())){
            indVirtualProductMapper.updatePurchase(dto);
        }
    }
}
