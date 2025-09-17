package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubscriptionPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubscriptionPageRO;
import com.norm.timemall.app.studio.mapper.StudioSubscriptionMapper;
import com.norm.timemall.app.studio.service.StudioSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioSubscriptionServiceImpl implements StudioSubscriptionService {
    @Autowired
    private StudioSubscriptionMapper studioSubscriptionMapper;
    @Override
    public IPage<StudioGetSubscriptionPageRO> fetchSubscriptions(StudioGetSubscriptionPageDTO dto) {

        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Page<StudioGetSubscriptionPageRO>  page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return studioSubscriptionMapper.selectPageByQ(page,dto,sellerBrandId);

    }
}
