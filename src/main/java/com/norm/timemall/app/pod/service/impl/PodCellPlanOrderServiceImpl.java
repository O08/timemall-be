package com.norm.timemall.app.pod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.AffiliateOrderTypeEnum;
import com.norm.timemall.app.base.enums.CellPlanOrderTagEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AffiliateOrder;
import com.norm.timemall.app.base.mo.CellPlanOrder;
import com.norm.timemall.app.base.pojo.dto.FetchCellPlanOrderPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodCellPlanOrderPageRO;
import com.norm.timemall.app.pod.mapper.PodAffiliateOrderMapper;
import com.norm.timemall.app.pod.mapper.PodCellPlanOrderMapper;
import com.norm.timemall.app.pod.service.PodCellPlanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PodCellPlanOrderServiceImpl implements PodCellPlanOrderService {
    @Autowired
    private PodCellPlanOrderMapper podCellPlanOrderMapper;

    @Autowired
    private PodAffiliateOrderMapper podAffiliateOrderMapper;


    @Override
    public IPage<PodCellPlanOrderPageRO> findCellPlanOrderPage(FetchCellPlanOrderPageDTO dto) {

        String userId=SecurityUserHelper.getCurrentPrincipal().getUserId();
        IPage<PodCellPlanOrderPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        IPage<PodCellPlanOrderPageRO> planOrder = podCellPlanOrderMapper.selectCellPlanOrderPage(page,userId,dto);
        return planOrder;

    }

    @Override
    public SuccessVO removeOneOrder(String id) {
        CellPlanOrder cellPlanOrder = podCellPlanOrderMapper.selectById(id);
        String currentUserId = SecurityUserHelper.getCurrentPrincipal().getUserId();

        // validated
        if(cellPlanOrder == null
                || !("" +CellPlanOrderTagEnum.WAITING_PAY.ordinal()).equals(cellPlanOrder.getTag())
                || !currentUserId.equals(cellPlanOrder.getConsumerId())
        ){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // del cell plan order data
        podCellPlanOrderMapper.deleteById(id);

        //  del cell plan order affiliate data
        LambdaQueryWrapper<AffiliateOrder> affiliateOrderWrappers = Wrappers.lambdaQuery();
        affiliateOrderWrappers.eq(AffiliateOrder::getOrderId,id)
                        .eq(AffiliateOrder::getOrderType, AffiliateOrderTypeEnum.PLAN.getMark());
        podAffiliateOrderMapper.delete(affiliateOrderWrappers);

        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
