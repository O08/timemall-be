package com.norm.timemall.app.pod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.pojo.dto.FetchCellPlanOrderPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodCellPlanOrderPageRO;
import org.springframework.stereotype.Service;

@Service
public interface PodCellPlanOrderService {


    IPage<PodCellPlanOrderPageRO> findCellPlanOrderPage(FetchCellPlanOrderPageDTO dto);
}
