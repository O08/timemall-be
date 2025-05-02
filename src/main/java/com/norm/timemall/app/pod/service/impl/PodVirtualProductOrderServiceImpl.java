package com.norm.timemall.app.pod.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.enums.VirtualOrderTagEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.VirtualOrder;
import com.norm.timemall.app.base.mo.VirtualProduct;
import com.norm.timemall.app.pod.domain.dto.PodFetchVirtualOrderPageDTO;
import com.norm.timemall.app.pod.domain.dto.PodVirtualOrderApplyRefundDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchVirtualOrderPageRO;
import com.norm.timemall.app.pod.domain.ro.PodGetVirtualOrderDeliverMaterialRO;
import com.norm.timemall.app.pod.domain.vo.PodGetVirtualOrderDeliverMaterialVO;
import com.norm.timemall.app.pod.mapper.PodVirtualOrderMapper;
import com.norm.timemall.app.pod.mapper.PodVirtualProductMapper;
import com.norm.timemall.app.pod.service.PodVirtualProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class PodVirtualProductOrderServiceImpl implements PodVirtualProductOrderService {

    @Autowired
    private PodVirtualOrderMapper podVirtualOrderMapper;

    @Autowired
    private PodVirtualProductMapper podVirtualProductMapper;

    @Override
    public IPage<PodFetchVirtualOrderPageRO> findOrderList(PodFetchVirtualOrderPageDTO dto) {

        String buyerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Page<PodFetchVirtualOrderPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return podVirtualOrderMapper.selectPageByQ(page,dto,buyerBrandId);

    }

    @Override
    public PodGetVirtualOrderDeliverMaterialVO findOrderDeliverMaterial(String orderId) {
        // validate role : seller \ buyer
        VirtualOrder order = podVirtualOrderMapper.selectById(orderId);
        String currentUserBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(order==null || !(currentUserBrandId.equals(order.getBuyerBrandId()) ||
            currentUserBrandId.equals(order.getSellerBrandId()))){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // query deliver info
        VirtualProduct product = podVirtualProductMapper.selectById(order.getProductId());
        PodGetVirtualOrderDeliverMaterialRO deliver = new PodGetVirtualOrderDeliverMaterialRO();
        deliver.setDeliverAttachment(product.getDeliverAttachment());
        deliver.setDeliverNote(product.getDeliverNote());

        PodGetVirtualOrderDeliverMaterialVO vo =new PodGetVirtualOrderDeliverMaterialVO();
        vo.setDeliver(deliver);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @Override
    public void applyRefund(PodVirtualOrderApplyRefundDTO dto) {
        // validate role must buyer
        VirtualOrder order = podVirtualOrderMapper.selectById(dto.getOrderId());
        String currentUserBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();

        if(order==null ){
            throw new QuickMessageException("未找到相关订单，操作失败");
        }
        if(!currentUserBrandId.equals(order.getBuyerBrandId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        if(SwitchCheckEnum.ENABLE.getMark().equals(order.getAlreadyRefund())){
            throw new ErrorCodeException(CodeEnum.REFUND_REPEAT);
        }
        if(SwitchCheckEnum.ENABLE.getMark().equals(order.getAlreadyRemittance())){
            throw new QuickMessageException("订单支持7天内退款，您的订单已过退款时间，操作失败");
        }

        order.setTag(VirtualOrderTagEnum.APPLY_REFUND.ordinal()+"");
        order.setModifiedAt(new Date());
        order.setRefundReason(dto.getRefundReason());

        podVirtualOrderMapper.updateById(order);

    }

}
