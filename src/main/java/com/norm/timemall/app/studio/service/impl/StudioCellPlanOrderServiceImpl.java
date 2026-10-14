package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.pojo.dto.FetchCellPlanOrderPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioCellPlanOrderPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchCellPlanOrderRO;
import com.norm.timemall.app.studio.mapper.StudioCellPlanOrderMapper;
import com.norm.timemall.app.studio.service.StudioCellPlanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioCellPlanOrderServiceImpl implements StudioCellPlanOrderService {
    @Autowired
    private StudioCellPlanOrderMapper studioCellPlanOrderMapper;
    @Override
    public IPage<StudioCellPlanOrderPageRO> findCellPlanOrderPage(FetchCellPlanOrderPageDTO dto) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        IPage<StudioCellPlanOrderPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        IPage<StudioCellPlanOrderPageRO> planOrder = studioCellPlanOrderMapper.selectCellPlanOrderPage(page,brandId,dto);
        return  planOrder;

    }

    @Override
    public StudioFetchCellPlanOrderRO findCellPlanOrder(String id) {
        StudioFetchCellPlanOrderRO orderRO= studioCellPlanOrderMapper.selectCellPlanOrderById(id);
        return orderRO;
    }

    @Override
    public void modifyCellPlanOrderTag(String id,String tag) {
        studioCellPlanOrderMapper.updateTagById(tag,id);

    }
}
