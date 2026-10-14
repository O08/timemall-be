package com.norm.timemall.app.affiliate.service.impl;

import com.norm.timemall.app.affiliate.domain.pojo.HotOutreach;
import com.norm.timemall.app.affiliate.domain.pojo.HotProduct;
import com.norm.timemall.app.affiliate.domain.ro.DashboardRO;
import com.norm.timemall.app.affiliate.domain.ro.HotOutreachRO;
import com.norm.timemall.app.affiliate.domain.ro.HotProductRO;
import com.norm.timemall.app.affiliate.mapper.AffiliateDashboardMapper;
import com.norm.timemall.app.affiliate.mapper.AffiliateHotOutreachMapper;
import com.norm.timemall.app.affiliate.mapper.AffiliateHotProductMapper;
import com.norm.timemall.app.affiliate.service.DashboardService;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private AffiliateDashboardMapper affiliateDashboardMapper;
    @Autowired
    private AffiliateHotProductMapper affiliateHotProductMapper;

    @Autowired
    private AffiliateHotOutreachMapper affiliateHotOutreachMapper;

    @Override
    public DashboardRO findDashboard(String timespan) {

        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return affiliateDashboardMapper.selectDashboardByBrandId(brandId,timespan);

    }

    @Override
    public HotProduct findHotProduct(String timespan) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        ArrayList<HotProductRO> ros = affiliateHotProductMapper.selectHotProductByBrandIdAndTimeSpan(brandId,timespan);
        HotProduct product = new HotProduct();
        product.setRecords(ros);
        return product;

    }

    @Override
    public HotOutreach findHotOutreach(String timespan) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        ArrayList<HotOutreachRO> ros = affiliateHotOutreachMapper.selectOutReachByBrandIdAndTimeSpan(brandId,timespan);
        HotOutreach outreach=new HotOutreach();
        outreach.setRecords(ros);
        return outreach;
    }
}
