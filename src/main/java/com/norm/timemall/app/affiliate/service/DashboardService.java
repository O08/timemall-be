package com.norm.timemall.app.affiliate.service;

import com.norm.timemall.app.affiliate.domain.pojo.HotOutreach;
import com.norm.timemall.app.affiliate.domain.pojo.HotProduct;
import com.norm.timemall.app.affiliate.domain.ro.DashboardRO;
import org.springframework.stereotype.Service;

@Service
public interface DashboardService {
    DashboardRO findDashboard(String timespan);

    HotProduct findHotProduct(String timespan);

    HotOutreach findHotOutreach(String timespan);
}
