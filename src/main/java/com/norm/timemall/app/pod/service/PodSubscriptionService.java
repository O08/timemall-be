package com.norm.timemall.app.pod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.pod.domain.dto.PodGetSubscriptionPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodGetSubscriptionPageRO;
import org.springframework.stereotype.Service;

@Service
public interface PodSubscriptionService {
    IPage<PodGetSubscriptionPageRO> findSubscription(PodGetSubscriptionPageDTO dto);

    void cancel(String id);


}
