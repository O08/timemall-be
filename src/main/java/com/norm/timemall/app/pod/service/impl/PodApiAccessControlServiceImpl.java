package com.norm.timemall.app.pod.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CellPlanOrder;
import com.norm.timemall.app.pod.mapper.PodCellPlanOrderMapper;
import com.norm.timemall.app.pod.service.PodApiAccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PodApiAccessControlServiceImpl implements PodApiAccessControlService {
    @Autowired
    private PodCellPlanOrderMapper podCellPlanOrderMapper;
    @Override
    public boolean isCellPlanOrderDeliverReceiver(String orderId,String tag) {

        LambdaQueryWrapper<CellPlanOrder> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(CellPlanOrder::getId,orderId)
                .eq(CellPlanOrder::getConsumerId, SecurityUserHelper.getCurrentPrincipal().getUserId());
        if(StrUtil.isNotEmpty(tag)){
            wrapper.eq(CellPlanOrder::getTag,tag);
        }
        return  podCellPlanOrderMapper.exists(wrapper);

    }
}
