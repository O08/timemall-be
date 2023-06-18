package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CommercialPaper;
import com.norm.timemall.app.base.mo.MpsChain;
import com.norm.timemall.app.studio.mapper.StudioCommercialPaperMapper;
import com.norm.timemall.app.studio.mapper.StudioMpsChainMapper;
import com.norm.timemall.app.studio.service.StudioApiAccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioApiAccessControlServiceImpl implements StudioApiAccessControlService {
    @Autowired
    private StudioMpsChainMapper studioMpsChainMapper;
    @Autowired
    private StudioCommercialPaperMapper studioCommercialPaperMapper;
    @Override
    public boolean isMpsChainFounder(String chainId) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<MpsChain> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(MpsChain::getId,chainId);
        wrapper.eq(MpsChain::getBrandId,brandId);
        return  studioMpsChainMapper.exists(wrapper);
    }

    @Override
    public boolean isMpscPaperFounder(String paperId) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<CommercialPaper> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(CommercialPaper::getId,paperId)
                .eq(CommercialPaper::getPurchaser,brandId);
        return studioCommercialPaperMapper.exists(wrapper);
    }
}
