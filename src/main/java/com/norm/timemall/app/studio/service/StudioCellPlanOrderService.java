package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.pojo.dto.FetchCellPlanOrderPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioCellPlanOrderPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchCellPlanOrderRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioCellPlanOrderService {
    IPage<StudioCellPlanOrderPageRO> findCellPlanOrderPage(FetchCellPlanOrderPageDTO dto);

    StudioFetchCellPlanOrderRO findCellPlanOrder(String id);
}
