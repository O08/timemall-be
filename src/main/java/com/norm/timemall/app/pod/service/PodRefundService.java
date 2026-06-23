package com.norm.timemall.app.pod.service;

import com.norm.timemall.app.pod.domain.dto.PodRefundDTO;
import org.springframework.stereotype.Service;

@Service
public interface PodRefundService {
    void refund(PodRefundDTO dto);
}
