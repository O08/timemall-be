package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.CellMarkEnum;
import com.norm.timemall.app.base.enums.CommercialPaperTagEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Cell;
import com.norm.timemall.app.base.mo.CommercialPaper;
import com.norm.timemall.app.base.mo.CommercialPaperDeliver;
import com.norm.timemall.app.base.mo.MpsChain;
import com.norm.timemall.app.studio.mapper.StudioCellMapper;
import com.norm.timemall.app.studio.mapper.StudioCommercialPaperDeliverMapper;
import com.norm.timemall.app.studio.mapper.StudioCommercialPaperMapper;
import com.norm.timemall.app.studio.mapper.StudioMpsChainMapper;
import com.norm.timemall.app.studio.service.StudioApiAccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioApiAccessControlServiceImpl implements StudioApiAccessControlService {
    @Autowired
    private StudioMpsChainMapper studioMpsChainMapper;
    @Autowired
    private StudioCommercialPaperMapper studioCommercialPaperMapper;
    @Autowired
    private StudioCommercialPaperDeliverMapper studioCommercialPaperDeliverMapper;
    @Autowired
    private StudioCellMapper studioCellMapper;
    @Override
    public boolean isMpsChainFounder(String chainId) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<MpsChain> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(MpsChain::getId,chainId);
        wrapper.eq(MpsChain::getBrandId,brandId);
        return  studioMpsChainMapper.exists(wrapper);
    }

    @Override
    public boolean isMpscPaperFounder(String paperId) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<CommercialPaper> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(CommercialPaper::getId,paperId)
                .eq(CommercialPaper::getPurchaser,brandId);
        return studioCommercialPaperMapper.exists(wrapper);
    }

    @Override
    public boolean isMpsPaperDeliverReceiver(String deliverId) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        CommercialPaperDeliver deliver = studioCommercialPaperDeliverMapper.selectPaperDeliverByIdAndBrandId(deliverId,brandId);
        return ObjectUtil.isNotEmpty(deliver);
    }

    @Override
    public boolean isMpsPaperDeliverSupplier(String paperId) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<CommercialPaper> queryWrapper=Wrappers.lambdaQuery();
        queryWrapper.eq(CommercialPaper::getId,paperId)
                        .eq(CommercialPaper::getSupplier,brandId)
                .eq(CommercialPaper::getTag, CommercialPaperTagEnum.DELIVERING.getMark());

        return studioCommercialPaperMapper.exists(queryWrapper);

    }

    @Override
    public boolean cellSupportCurrentBrandModify(String cellId) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Cell cell = studioCellMapper.selectById(cellId);
        return cell!=null && brandId.equals(cell.getBrandId()) && (!CellMarkEnum.ONLINE.getMark().equals(cell.getMark()));

    }

    @Override
    public boolean isCellPlanOrderSupplier(String orderId) {
        String brandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();

        Cell cell = studioCellMapper.selectCellByCellPlanOrderId(orderId);
        return cell!=null && brandId.equals(cell.getBrandId());

    }
}
