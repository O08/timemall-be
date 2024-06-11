package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.AffiliateOrderMarketEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AffiliateInfluencerProduct;
import com.norm.timemall.app.base.mo.AffiliateLinkMarketing;
import com.norm.timemall.app.base.mo.AffiliateOrder;
import com.norm.timemall.app.mall.mapper.MallAffiliateInfluencerProductMapper;
import com.norm.timemall.app.mall.mapper.MallAffiliateLinkMarketingMapper;
import com.norm.timemall.app.mall.mapper.MallAffiliateOrderMapper;
import com.norm.timemall.app.mall.service.MallAffiliateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class MallAffiliateOrderServiceImpl implements MallAffiliateOrderService {

    @Autowired
    private MallAffiliateOrderMapper mallAffiliateOrderMapper;
    @Autowired
    private MallAffiliateInfluencerProductMapper mallAffiliateInfluencerProductMapper;
    @Autowired
    private MallAffiliateLinkMarketingMapper mallAffiliateLinkMarketingMapper;

    @Async
    @Override
    public void newAffiliateOrder(String cellId, String orderId, String orderType, BigDecimal price, String influencer, String chn, String market) {
        // access block
        if(!(AffiliateOrderMarketEnum.API.getMark().equals(market) || AffiliateOrderMarketEnum.LINK.getMark().equals(market))){
            return;
        }
        // validate cellId
        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<AffiliateInfluencerProduct> influencerProductLambdaQueryWrapper= Wrappers.lambdaQuery();
        influencerProductLambdaQueryWrapper.eq(AffiliateInfluencerProduct::getBrandId,brandId)
                .eq(AffiliateInfluencerProduct::getCellId,cellId);
        AffiliateInfluencerProduct product = mallAffiliateInfluencerProductMapper.selectOne(influencerProductLambdaQueryWrapper);
        if(product==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // if market is link, validate
        AffiliateLinkMarketing linkMarketing=null;
        if(AffiliateOrderMarketEnum.LINK.getMark().equals(market)){
            LambdaQueryWrapper<AffiliateLinkMarketing> affiliateLinkMarketingLambdaQueryWrapper=Wrappers.lambdaQuery();
            affiliateLinkMarketingLambdaQueryWrapper.eq(AffiliateLinkMarketing::getBrandId,brandId)
                    .eq(AffiliateLinkMarketing::getCellId,cellId)
                    .eq(AffiliateLinkMarketing::getOutreachChannelId,chn);
               linkMarketing = mallAffiliateLinkMarketingMapper.selectOne(affiliateLinkMarketingLambdaQueryWrapper);
        }
        if(AffiliateOrderMarketEnum.LINK.getMark().equals(market) && linkMarketing==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        AffiliateOrder order = new AffiliateOrder();
        order.setId(IdUtil.simpleUUID())
                .setOrderId(orderId)
                .setOrderType(orderType)
                .setInfluencer(influencer)
                .setOutreachChannelId(chn)
                .setRevshare(product.getRevshare())
                .setPrice(price)
                .setMarket(market)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        mallAffiliateOrderMapper.insert(order);

    }
}
