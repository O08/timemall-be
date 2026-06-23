package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.VirtualOrder;
import com.norm.timemall.app.studio.domain.dto.StudioChangeVirtualOrderPackDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchVirtualOrderListPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchVirtualOrderMaintenanceDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchVirtualOrderListPageRO;
import com.norm.timemall.app.studio.handler.StudioVirtualOrderRemittanceHandler;
import com.norm.timemall.app.studio.mapper.StudioVirtualOrderMapper;
import com.norm.timemall.app.studio.service.StudioVirtualProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioVirtualProductOrderServiceImpl implements StudioVirtualProductOrderService {

    @Autowired
    private StudioVirtualOrderMapper studioVirtualOrderMapper;

    @Autowired
    private StudioVirtualOrderRemittanceHandler studioVirtualOrderRemittanceHandler;



    @Override
    public IPage<StudioFetchVirtualOrderListPageRO> findOrders(StudioFetchVirtualOrderListPageDTO dto) {

        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Page<StudioFetchVirtualOrderListPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return studioVirtualOrderMapper.selectPageByQ(page,dto,sellerBrandId);

    }

    @Override
    public void fromPlatformRemittanceToSeller(String orderId) {
        studioVirtualOrderRemittanceHandler.doRemittance(orderId);
    }

    @Override
    public void orderMaintenance(StudioFetchVirtualOrderMaintenanceDTO dto) {

        VirtualOrder order = studioVirtualOrderMapper.selectById(dto.getOrderId());
        if(order ==null){
            throw new QuickMessageException("未找到相关订单");
        }

        // role validated
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller= currentUserBrandId.equals(order.getSellerBrandId());

        if(!isSeller){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        order.setTag(dto.getTag());
        order.setModifiedAt(new Date());
        studioVirtualOrderMapper.updateById(order);

    }

    @Override
    public void modifyPack(StudioChangeVirtualOrderPackDTO dto) {

        VirtualOrder order = studioVirtualOrderMapper.selectById(dto.getOrderId());
        if(order ==null){
            throw new QuickMessageException("未找到相关订单");
        }
        // role validated
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller= currentUserBrandId.equals(order.getSellerBrandId());

        if(!isSeller){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        order.setPack(dto.getPack());
        order.setModifiedAt(new Date());
        studioVirtualOrderMapper.updateById(order);

    }
}
