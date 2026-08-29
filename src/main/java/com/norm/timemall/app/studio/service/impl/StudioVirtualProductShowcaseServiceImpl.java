package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.mo.VirtualProductShowcase;
import com.norm.timemall.app.studio.mapper.StudioVirtualProductShowcaseMapper;
import com.norm.timemall.app.studio.service.StudioVirtualProductShowcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioVirtualProductShowcaseServiceImpl implements StudioVirtualProductShowcaseService {

    @Autowired
    private StudioVirtualProductShowcaseMapper studioVirtualProductShowcaseMapper;

    @Override
    public Long findMaxShowCaseOd(String productId) {
        LambdaQueryWrapper<VirtualProductShowcase> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(VirtualProductShowcase::getProductId,productId);
        return studioVirtualProductShowcaseMapper.selectCount(wrapper);
    }

    @Override
    public void newShowcase(String productId, String showcaseUrl, Long nextShowcaseOd) {

        VirtualProductShowcase showcase = new VirtualProductShowcase();
        showcase.setId(IdUtil.simpleUUID())
                .setProductId(productId)
                .setShowcaseUrl(showcaseUrl)
                .setShowcaseOd(nextShowcaseOd)
                .setModifiedAt(new Date())
                .setCreateAt(new Date());

        studioVirtualProductShowcaseMapper.insert(showcase);

    }

    @Override
    public VirtualProductShowcase findOneCase(String showcaseId) {
        return studioVirtualProductShowcaseMapper.selectById(showcaseId);
    }

    @Override
    public void modifyShowcase(String showcaseId, String showcaseUrl) {

        VirtualProductShowcase showcase = new VirtualProductShowcase();
        showcase.setId(showcaseId)
                .setShowcaseUrl(showcaseUrl)
                .setModifiedAt(new Date());
        studioVirtualProductShowcaseMapper.updateById(showcase);

    }
}
