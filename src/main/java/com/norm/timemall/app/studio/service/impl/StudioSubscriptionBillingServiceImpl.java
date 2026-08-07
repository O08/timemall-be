package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubsBillingPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsBillingPageRO;
import com.norm.timemall.app.studio.mapper.StudioSubsBillMapper;
import com.norm.timemall.app.studio.service.StudioSubscriptionBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioSubscriptionBillingServiceImpl implements StudioSubscriptionBillingService {
    @Autowired
    private StudioSubsBillMapper studioSubsBillMapper;

    @Override
    public IPage<StudioGetSubsBillingPageRO> findBills(StudioGetSubsBillingPageDTO dto) {
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Page<StudioGetSubsBillingPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return studioSubsBillMapper.selectPageByQ(page,dto,sellerBrandId);
    }
}
