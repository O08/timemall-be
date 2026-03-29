package com.norm.timemall.app.studio.service.impl;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.studio.domain.ro.StudioFetchSellerDashBoardRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchSellerOpenStatsInfoRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchSellerDashBoardVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchSellerOpenStatsInfoVO;
import com.norm.timemall.app.studio.mapper.StudioSellerDashboardMapper;
import com.norm.timemall.app.studio.service.StudioSellerDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioSellerDashboardServiceImpl implements StudioSellerDashboardService {
    @Autowired
    private StudioSellerDashboardMapper studioSellerDashboardMapper;

    @Override
    public StudioFetchSellerDashBoardVO findSellerDashboard() {

        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        StudioFetchSellerDashBoardRO ro =studioSellerDashboardMapper.selectDashboardByBrand(currentBrandId);

        StudioFetchSellerDashBoardVO vo = new StudioFetchSellerDashBoardVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setDashboard(ro);
        return vo;

    }

    @Override
    public StudioFetchSellerOpenStatsInfoVO findSellerOpenStatsInfo(String sellerBrandId) {
        StudioFetchSellerOpenStatsInfoRO ro = studioSellerDashboardMapper.selectSellerOpenStatsInfo(sellerBrandId);
        StudioFetchSellerOpenStatsInfoVO vo = new StudioFetchSellerOpenStatsInfoVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setStats(ro);
        return vo;
    }
}
