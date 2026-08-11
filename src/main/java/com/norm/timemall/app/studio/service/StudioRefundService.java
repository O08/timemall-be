package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.dto.StudioRefundDTO;
import org.springframework.stereotype.Service;

@Service
public interface StudioRefundService {
    void refund(StudioRefundDTO dto);
}
