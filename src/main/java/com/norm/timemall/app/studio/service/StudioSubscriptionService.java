package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubscriptionPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubscriptionPageRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioSubscriptionService {
    IPage<StudioGetSubscriptionPageRO> fetchSubscriptions(StudioGetSubscriptionPageDTO dto);

}
