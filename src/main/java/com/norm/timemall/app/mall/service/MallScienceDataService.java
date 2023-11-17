package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.dto.ScienceSemiDataDTO;
import org.springframework.stereotype.Service;

@Service
public interface MallScienceDataService {
    void addNewScienceSemiData(ScienceSemiDataDTO dto);
}
