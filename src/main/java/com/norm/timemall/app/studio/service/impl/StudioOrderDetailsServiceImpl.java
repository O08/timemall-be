package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.studio.domain.dto.StudioWorkflowPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioTransRO;
import com.norm.timemall.app.studio.mapper.StudioOrderDetailsMapper;
import com.norm.timemall.app.studio.service.StudioOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioOrderDetailsServiceImpl implements StudioOrderDetailsService {
    @Autowired
    private StudioOrderDetailsMapper studioOrderDetailsMapper;


    @Override
    public IPage<StudioTransRO> findWorkflowForBrand(String brandId, StudioWorkflowPageDTO dto) {
        IPage<StudioTransRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        IPage<StudioTransRO> ro = studioOrderDetailsMapper.selectWorkflowPageByBrandId(page,brandId,dto);
        return ro;
    }
}
