package com.norm.timemall.app.pod.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.SubsBillStatusEnum;
import com.norm.timemall.app.base.enums.SubscriptionStatusEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.SubsBill;
import com.norm.timemall.app.base.mo.Subscription;
import com.norm.timemall.app.pod.domain.dto.PodGetSubscriptionPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodGetSubscriptionPageRO;
import com.norm.timemall.app.pod.mapper.PodSubsBillMapper;
import com.norm.timemall.app.pod.mapper.PodSubscriptionMapper;
import com.norm.timemall.app.pod.service.PodSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PodSubscriptionServiceImpl implements PodSubscriptionService {
    @Autowired
    private PodSubscriptionMapper podSubscriptionMapper;
    @Autowired
    private PodSubsBillMapper podSubsBillMapper;

    @Override
    public IPage<PodGetSubscriptionPageRO> findSubscription(PodGetSubscriptionPageDTO dto) {
        Page<PodGetSubscriptionPageRO>  page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        String buyerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return podSubscriptionMapper.selectPageByQ(page,dto,buyerBrandId);
    }

    @Override
    public void cancel(String id) {
        Subscription subscription = podSubscriptionMapper.selectById(id);
        if(subscription==null){
            throw new QuickMessageException("未发现相关订阅");
        }
        String buyerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(!buyerBrandId.equals(subscription.getSubscriberFid())){
            throw new QuickMessageException("非法操作");
        }
        if(SubscriptionStatusEnum.INCOMPLETE_EXPIRED.getMark().equals(subscription.getStatus())
        || SubscriptionStatusEnum.CANCELED.getMark().equals(subscription.getStatus())
        || SubscriptionStatusEnum.CLOSED.getMark().equals(subscription.getStatus())){
            throw new QuickMessageException("订阅已终止，请勿重复操作");
        }

        subscription.setStatus(SubscriptionStatusEnum.CANCELED.getMark());
        subscription.setModifiedAt(new Date());
        subscription.setCanceledAt(new Date());
        LambdaUpdateWrapper<Subscription> subscriptionLambdaUpdateWrapper= Wrappers.lambdaUpdate();
        subscriptionLambdaUpdateWrapper.eq(Subscription::getId,subscription.getId());
        podSubscriptionMapper.update(subscription,subscriptionLambdaUpdateWrapper);

        // update date trial subscription bill as void
        if(DateUtil.compare(new Date(),subscription.getTrialPeriodEndAt())<0){
            LambdaQueryWrapper<SubsBill> billLambdaQueryWrapperWrapper=Wrappers.lambdaQuery();
            billLambdaQueryWrapperWrapper.eq(SubsBill::getPlanId,subscription.getPlanId())
                              .eq(SubsBill::getBuyerFidType, FidTypeEnum.BRAND.getMark())
                               .eq(SubsBill::getBuyerFid,buyerBrandId)
                               .eq(SubsBill::getStatus, SubsBillStatusEnum.FREEZE.getMark());

            SubsBill bill = podSubsBillMapper.selectOne(billLambdaQueryWrapperWrapper);
            bill.setStatus(SubsBillStatusEnum.VOID.getMark());
            bill.setModifiedAt(new Date());
            podSubsBillMapper.updateById(bill);

        }




    }
}
