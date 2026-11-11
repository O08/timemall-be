package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.StudioChangePhysicalOrderStatusDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchPhysicalOrderPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPhysicalOrderDeliveryDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderDeliveryInfoRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderDetailRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderPageRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioPhysicalOrderService {

    IPage<StudioFetchPhysicalOrderPageRO> fetchOrderPage(StudioFetchPhysicalOrderPageDTO dto);

    StudioFetchPhysicalOrderDetailRO fetchOrderDetail(String orderId);

    StudioFetchPhysicalOrderDeliveryInfoRO fetchDeliveryInfo(String orderId);

    void delivery(StudioPhysicalOrderDeliveryDTO dto);

    void remit(String orderId);

    void changeOrderStatus(StudioChangePhysicalOrderStatusDTO dto);

}
