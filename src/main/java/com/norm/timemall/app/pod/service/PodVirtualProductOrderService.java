package com.norm.timemall.app.pod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.pod.domain.dto.PodFetchVirtualOrderPageDTO;
import com.norm.timemall.app.pod.domain.dto.PodVirtualOrderApplyRefundDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchVirtualOrderPageRO;
import com.norm.timemall.app.pod.domain.vo.PodGetVirtualOrderDeliverMaterialVO;
import org.springframework.stereotype.Service;

@Service
public interface PodVirtualProductOrderService {
    IPage<PodFetchVirtualOrderPageRO> findOrderList(PodFetchVirtualOrderPageDTO dto);

    PodGetVirtualOrderDeliverMaterialVO findOrderDeliverMaterial(String orderId);


    void applyRefund(PodVirtualOrderApplyRefundDTO dto);

}
