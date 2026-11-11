package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubsBillingPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsBillingPageRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioSubscriptionBillingService {
    IPage<StudioGetSubsBillingPageRO> findBills(StudioGetSubsBillingPageDTO dto);
}
