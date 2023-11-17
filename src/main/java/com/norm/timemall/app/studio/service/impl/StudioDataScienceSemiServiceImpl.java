package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.studio.domain.dto.FetchSemiDataPageDTO;
import com.norm.timemall.app.studio.domain.ro.FetchSemiDataRO;
import com.norm.timemall.app.studio.mapper.StudioDataScienceSemiMapper;
import com.norm.timemall.app.studio.service.StudioDataScienceSemiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioDataScienceSemiServiceImpl implements StudioDataScienceSemiService {
    @Autowired
    private StudioDataScienceSemiMapper studioDataScienceSemiMapper;
    @Override
    public IPage<FetchSemiDataRO> findSemiDataPage(FetchSemiDataPageDTO dto) {
        IPage<FetchSemiDataRO> page= new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return studioDataScienceSemiMapper.selectSemiDataPage(page,dto);
    }
}
