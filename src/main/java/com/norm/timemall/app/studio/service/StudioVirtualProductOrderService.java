package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.StudioFetchVirtualOrderListPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchVirtualOrderMaintenanceDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchVirtualOrderListPageRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioVirtualProductOrderService {
    IPage<StudioFetchVirtualOrderListPageRO> findOrders(StudioFetchVirtualOrderListPageDTO dto);

    void fromPlatformRemittanceToSeller(String orderId);

    void orderMaintenance(StudioFetchVirtualOrderMaintenanceDTO dto);
}
