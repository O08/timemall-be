package com.norm.timemall.app.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.mall.domain.dto.MallFetchPhysicalProductPageDTO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPhysicalProductPageRO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPhysicalProductInfoRO;
import com.norm.timemall.app.mall.mapper.MallPhysicalProductMapper;
import com.norm.timemall.app.mall.service.MallPhysicalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MallPhysicalProductServiceImpl implements MallPhysicalProductService {
    @Autowired
    private MallPhysicalProductMapper physicalProductMapper;

    @Override
    public IPage<MallFetchPhysicalProductPageRO> findBrandProducts(MallFetchPhysicalProductPageDTO dto) {
        Page<MallFetchPhysicalProductPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return physicalProductMapper.selectPageByBrandAndQ(page, dto);
    }

    @Override
    public MallFetchPhysicalProductInfoRO findProductProfile(String productId) {
        return physicalProductMapper.selectProfileInfo(productId);
    }
}
