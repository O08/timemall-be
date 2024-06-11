package com.norm.timemall.app.indicator.service;

import com.norm.timemall.app.indicator.domain.dto.IndDataLayerCellIndicesDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface IndAffiliateAccessService {
    void newAccess(IndDataLayerCellIndicesDTO dto, HttpServletRequest request);
}
