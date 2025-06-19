package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.vo.StudioFetchSellerDashBoardVO;
import org.springframework.stereotype.Service;

@Service
public interface StudioSellerDashboardService {
    StudioFetchSellerDashBoardVO findSellerDashboard();


}
