package com.norm.timemall.app.pod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.pod.domain.dto.PodFetchPhysicalOrderPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchPhysicalOrderDetailRO;
import com.norm.timemall.app.pod.domain.ro.PodFetchPhysicalOrderPageRO;

public interface PodPhysicalOrderService {
    IPage<PodFetchPhysicalOrderPageRO> findOrderList(PodFetchPhysicalOrderPageDTO dto);

    PodFetchPhysicalOrderDetailRO fetchOrderDetail(String orderId);
}
