package com.norm.timemall.app.indicator.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.indicator.domain.ro.IndCellIndicesRO;
import com.norm.timemall.app.indicator.mapper.IndCellIndicesMapper;
import com.norm.timemall.app.indicator.service.CellIndicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CellIndicesServiceImpl implements CellIndicesService {
    @Autowired
    private IndCellIndicesMapper indCellIndicesMapper;
    @Override
    public IPage<IndCellIndicesRO> findCellIndices(PageDTO pageDTO, CustomizeUser user) {
        IPage<IndCellIndicesRO>  page = new Page<>();
        page.setCurrent(pageDTO.getCurrent());
        page.setSize(pageDTO.getSize());
        IPage<IndCellIndicesRO> cells = indCellIndicesMapper.selectCellIndicesPageByUserId(page, user.getUserId());
        return cells;
    }
}
