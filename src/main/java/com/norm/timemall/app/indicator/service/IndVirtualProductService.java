package com.norm.timemall.app.indicator.service;

import com.norm.timemall.app.indicator.domain.dto.IndDataLayerVirtualProductIndicesDTO;
import org.springframework.stereotype.Service;

@Service
public interface IndVirtualProductService {
    void modifyIndices(IndDataLayerVirtualProductIndicesDTO dto);
}
