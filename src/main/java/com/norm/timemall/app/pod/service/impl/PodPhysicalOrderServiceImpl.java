package com.norm.timemall.app.pod.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.pod.domain.dto.PodFetchPhysicalOrderPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchPhysicalOrderDetailRO;
import com.norm.timemall.app.pod.domain.ro.PodFetchPhysicalOrderPageRO;
import com.norm.timemall.app.pod.mapper.PodPhysicalOrderMapper;
import com.norm.timemall.app.pod.service.PodPhysicalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PodPhysicalOrderServiceImpl implements PodPhysicalOrderService {

    @Autowired
    private PodPhysicalOrderMapper physicalOrderMapper;

    @Override
    public IPage<PodFetchPhysicalOrderPageRO> findOrderList(PodFetchPhysicalOrderPageDTO dto) {
        String buyerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        Page<PodFetchPhysicalOrderPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());

        return physicalOrderMapper.selectOrderPage(page, dto, buyerBrandId);
    }

    @Override
    public PodFetchPhysicalOrderDetailRO fetchOrderDetail(String orderId) {
        String buyerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return physicalOrderMapper.selectDetailByBrandAndId(orderId, buyerBrandId);
    }
}
