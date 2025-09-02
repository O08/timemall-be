package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.ElectricityBusinessTypeEnum;
import com.norm.timemall.app.base.enums.TransDirectionEnum;
import com.norm.timemall.app.base.mapper.BaseElectricityHistoryMapper;
import com.norm.timemall.app.base.mo.ElectricityHistory;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.service.BaseElectricityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BaseElectricityServiceImpl implements BaseElectricityService {
    @Autowired
    private BaseElectricityHistoryMapper baseElectricityHistoryMapper;
    @Autowired
    private AccountService accountService;

    @Override
    public Long findShoppingFrequencyEveryMonth(String buyerBrandId) {
        Date target=new Date();
        Date beginDate = DateUtil.beginOfMonth(target);
        Date endDate = DateUtil.endOfMonth(target);

        LambdaQueryWrapper<ElectricityHistory> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(ElectricityHistory::getBuyerBrandId,buyerBrandId);
        wrapper.eq(ElectricityHistory::getBusiType, ElectricityBusinessTypeEnum.TOP_UP.getMark());
        wrapper.lt(ElectricityHistory::getCreateAt,endDate);
        wrapper.gt(ElectricityHistory::getCreateAt,beginDate);

        return baseElectricityHistoryMapper.selectCount(wrapper);

    }

    @Override
    public void topup(String buyerBrandId, int defaultPoints,String item,String businessType,String outNo,String clue) {
        accountService.topUpElectricity(buyerBrandId,defaultPoints);
        ElectricityHistory history=new ElectricityHistory();
        history.setId(IdUtil.simpleUUID())
                .setItem(item)
                .setBusiType(businessType)
                .setBuyerBrandId(buyerBrandId)
                .setAmount(defaultPoints)
                .setDirection(TransDirectionEnum.CREDIT.getMark())
                .setOutNo(outNo)
                .setClue(clue)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        baseElectricityHistoryMapper.insert(history);
    }
}
