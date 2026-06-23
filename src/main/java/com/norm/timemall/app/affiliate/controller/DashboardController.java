package com.norm.timemall.app.affiliate.controller;

import com.norm.timemall.app.affiliate.domain.pojo.HotOutreach;
import com.norm.timemall.app.affiliate.domain.pojo.HotProduct;
import com.norm.timemall.app.affiliate.domain.ro.DashboardRO;
import com.norm.timemall.app.affiliate.domain.vo.DashboardVO;
import com.norm.timemall.app.affiliate.domain.vo.HotOutreachVO;
import com.norm.timemall.app.affiliate.domain.vo.HotProductVO;
import com.norm.timemall.app.affiliate.service.DashboardService;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;
    @ResponseBody
    @GetMapping("/api/v1/web/affiliate/dashboard")
    public DashboardVO fetchDashboard(String timespan){

        validateTimeSpan(timespan);
        DashboardRO dashboardRO= dashboardService.findDashboard(timespan);
        DashboardVO vo = new DashboardVO();
        vo.setDashboard(dashboardRO);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @ResponseBody
    @GetMapping("/api/v1/web/affiliate/hot_product")
    public HotProductVO fetchHotProduct(String timespan){

        validateTimeSpan(timespan);
        HotProduct hotProduct=dashboardService.findHotProduct(timespan);
        HotProductVO vo = new HotProductVO();
        vo.setProduct(hotProduct);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @ResponseBody
    @GetMapping("/api/v1/web/affiliate/hot_outreach")
    public HotOutreachVO fetchHotOutreach(String timespan){

        validateTimeSpan(timespan);
        HotOutreach outreach = dashboardService.findHotOutreach(timespan);
        HotOutreachVO vo = new HotOutreachVO();
        vo.setOutreach(outreach);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    private void  validateTimeSpan(String timespan){
        if(!("week".equals(timespan) || "yesterday".equals(timespan) || "month".equals(timespan))){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
    }
}
