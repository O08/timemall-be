package com.norm.timemall.app.pod.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.pojo.dto.FetchCellPlanOrderPageDTO;
import com.norm.timemall.app.pod.domain.pojo.PodBrandAndPriceBO;
import com.norm.timemall.app.pod.domain.ro.PodCellPlanOrderPageRO;
import com.norm.timemall.app.pod.mapper.PodCellPlanOrderMapper;
import com.norm.timemall.app.pod.service.PodCellPlanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PodCellPlanOrderServiceImpl implements PodCellPlanOrderService {
    @Autowired
    private PodCellPlanOrderMapper podCellPlanOrderMapper;
    @Override
    public void modifyPlanOrderTagForCurrentUser(String orderId, int tag) {

        String userId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        podCellPlanOrderMapper.updateTagByConsumerIdAndId(orderId,userId,tag);

    }

    @Override
    public PodBrandAndPriceBO findSupplierBrandInfo(String orderId) {
        return podCellPlanOrderMapper.selectBrandAndAmountById(orderId);
    }

    @Override
    public IPage<PodCellPlanOrderPageRO> findCellPlanOrderPage(FetchCellPlanOrderPageDTO dto) {

        String userId=SecurityUserHelper.getCurrentPrincipal().getUserId();
        IPage<PodCellPlanOrderPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        IPage<PodCellPlanOrderPageRO> planOrder = podCellPlanOrderMapper.selectCellPlanOrderPage(page,userId,dto);
        return planOrder;

    }
}
