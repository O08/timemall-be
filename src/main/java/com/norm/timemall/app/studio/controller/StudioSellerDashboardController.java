package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.studio.domain.vo.StudioFetchSellerDashBoardVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchSellerOpenStatsInfoVO;
import com.norm.timemall.app.studio.service.StudioSellerDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioSellerDashboardController {
    @Autowired
    private StudioSellerDashboardService studioSellerDashboardService;

    @GetMapping(value = "/api/v1/web_estudio/seller/dashboard")
    public StudioFetchSellerDashBoardVO fetchSellerDashBoard(){
      return  studioSellerDashboardService.findSellerDashboard();
    }
    @GetMapping("/api/open/seller/{id}/statistics")
    public StudioFetchSellerOpenStatsInfoVO fetchSellerStatistics(@PathVariable String id){
        return studioSellerDashboardService.findSellerOpenStatsInfo(id);
    }
}
