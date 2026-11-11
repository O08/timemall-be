package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.PhysicalOrderShippingStatusEnum;
import com.norm.timemall.app.base.enums.PhysicalOrderStatusEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.PhysicalOrder;
import com.norm.timemall.app.studio.domain.dto.StudioChangePhysicalOrderStatusDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchPhysicalOrderPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPhysicalOrderDeliveryDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderDeliveryInfoRO;
import com.norm.timemall.app.studio.handler.StudioPhysicalOrderRemittanceHandler;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderDetailRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderPageRO;
import com.norm.timemall.app.studio.mapper.StudioPhysicalOrderMapper;
import com.norm.timemall.app.studio.service.StudioPhysicalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioPhysicalOrderServiceImpl implements StudioPhysicalOrderService {
    @Autowired
    private StudioPhysicalOrderMapper physicalOrderMapper;
    @Autowired
    private StudioPhysicalOrderRemittanceHandler physicalOrderRemittanceHandler;

    @Override
    public IPage<StudioFetchPhysicalOrderPageRO> fetchOrderPage(StudioFetchPhysicalOrderPageDTO dto) {
        String sellerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Page<StudioFetchPhysicalOrderPageRO> page = new Page<>(dto.getCurrent(), dto.getSize());
        return physicalOrderMapper.selectPageByBrandAndQ(page, sellerBrandId, dto);
    }

    @Override
    public StudioFetchPhysicalOrderDetailRO fetchOrderDetail(String orderId) {
        String sellerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        StudioFetchPhysicalOrderDetailRO detail = physicalOrderMapper.selectDetailByBrandAndId(orderId, sellerBrandId);

        return detail;
    }

    @Override
    public StudioFetchPhysicalOrderDeliveryInfoRO fetchDeliveryInfo(String orderId) {
        String sellerOrBuyerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        StudioFetchPhysicalOrderDeliveryInfoRO info = physicalOrderMapper.selectDeliveryInfoByBrandAndId(orderId, sellerOrBuyerBrandId);
        return info;
    }

    @Override
    public void delivery(StudioPhysicalOrderDeliveryDTO dto) {
        String sellerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        PhysicalOrder order = physicalOrderMapper.selectForUpdate(dto.getOrderId());
        if (order == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        // only the seller of this order can ship it
        if (!sellerBrandId.equals(order.getSellerBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        // only a paid order can be shipped
        if (!PhysicalOrderStatusEnum.PAID.getMark().equals(order.getOrderStatus())) {
            throw new ErrorCodeException(CodeEnum.INVALID_STATUS);
        }

        // precisely update shipping fields + transition to shipped
        LambdaUpdateWrapper<PhysicalOrder> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PhysicalOrder::getId, dto.getOrderId())
                .eq(PhysicalOrder::getSellerBrandId, sellerBrandId)
                .set(PhysicalOrder::getShippingMethod, dto.getShippingMethod())
                .set(PhysicalOrder::getShippingNumber, dto.getShippingNumber())
                .set(PhysicalOrder::getShippingNote, dto.getShippingNote())
                .set(PhysicalOrder::getCourierPhone, dto.getCourierPhone())
                .set(PhysicalOrder::getShippingStatus, PhysicalOrderShippingStatusEnum.SHIPPED.getMark())
                .set(PhysicalOrder::getOrderStatus, PhysicalOrderStatusEnum.SHIPPED.getMark())
                .set(PhysicalOrder::getModifiedAt, new Date());
        physicalOrderMapper.update(null, updateWrapper);
    }

    @Override
    public void remit(String orderId) {
        physicalOrderRemittanceHandler.doRemittance(orderId);
    }

    @Override
    public void changeOrderStatus(StudioChangePhysicalOrderStatusDTO dto) {
        String sellerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        PhysicalOrder order = physicalOrderMapper.selectForUpdate(dto.getOrderId());
        if (order == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        // only the seller of this order can change its status
        if (!sellerBrandId.equals(order.getSellerBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        // 该接口仅支持将订单状态调整为完成，其余状态暂不允许调整（后续有需要再放开）
        if (!PhysicalOrderStatusEnum.COMPLETED.getMark().equals(dto.getOrderStatus())) {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // 如果订单状态不为已发货，拒绝操作
        if(!PhysicalOrderStatusEnum.SHIPPED.getMark().equals(order.getOrderStatus())){
            throw new ErrorCodeException(CodeEnum.INVALID_STATUS);
        }

        // 精确更新订单状态字段，避免全量更新覆盖其他列
        LambdaUpdateWrapper<PhysicalOrder> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PhysicalOrder::getId, dto.getOrderId())
                .eq(PhysicalOrder::getSellerBrandId, sellerBrandId)
                .set(PhysicalOrder::getShippingStatus,PhysicalOrderShippingStatusEnum.DELIVERED.getMark())
                .set(PhysicalOrder::getOrderStatus, PhysicalOrderStatusEnum.COMPLETED.getMark())
                .set(PhysicalOrder::getModifiedAt, new Date());
        physicalOrderMapper.update(null, updateWrapper);
    }

}
