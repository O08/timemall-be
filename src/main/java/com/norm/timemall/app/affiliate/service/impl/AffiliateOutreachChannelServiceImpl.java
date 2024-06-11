package com.norm.timemall.app.affiliate.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.affiliate.domain.dto.FetchOutreachChannelPageDTO;
import com.norm.timemall.app.affiliate.domain.dto.RenameChannelNameDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchOutreachChannelPageRO;
import com.norm.timemall.app.affiliate.mapper.AffiliateLinkMarketingMapper;
import com.norm.timemall.app.affiliate.service.AffiliateOutreachChannelService;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.affiliate.mapper.AffiliateOutreachChannelMapper;
import com.norm.timemall.app.base.mo.AffiliateLinkMarketing;
import com.norm.timemall.app.base.mo.AffiliateOutreachChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AffiliateOutreachChannelServiceImpl implements AffiliateOutreachChannelService {
    @Autowired
    private AffiliateOutreachChannelMapper affiliateOutreachChannelMapper;
    @Autowired
    private AffiliateLinkMarketingMapper affiliateLinkMarketingMapper;


    @Override
    public void modifyChannelName(RenameChannelNameDTO dto) {
        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        affiliateOutreachChannelMapper.updateChannelNameByIdAndBrand(brandId,dto);
    }

    @Override
    public void removeChannel(String outreachChannelId) {
        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // validate
        LambdaQueryWrapper<AffiliateLinkMarketing> apiMarketingWrapper= Wrappers.lambdaQuery();
        apiMarketingWrapper.eq(AffiliateLinkMarketing::getBrandId,brandId)
                .eq(AffiliateLinkMarketing::getOutreachChannelId,outreachChannelId);
        boolean exists = affiliateLinkMarketingMapper.exists(apiMarketingWrapper);
        if(exists){
            throw new ErrorCodeException(CodeEnum.AFFILIATE_LINKS_EXIST);
        }
        LambdaQueryWrapper<AffiliateOutreachChannel> delChannelWrappers= Wrappers.lambdaQuery();
        delChannelWrappers.eq(AffiliateOutreachChannel::getBrandId,brandId)
                .eq(AffiliateOutreachChannel::getId,outreachChannelId);
        affiliateOutreachChannelMapper.delete(delChannelWrappers);

    }

    @Override
    public void addOneChannel(String outreachName) {
        String brandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // validate
        LambdaQueryWrapper<AffiliateOutreachChannel> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(AffiliateOutreachChannel::getChannelName,outreachName)
                .eq(AffiliateOutreachChannel::getBrandId,brandId);
        boolean exists = affiliateOutreachChannelMapper.exists(wrapper);
        if(exists){
            throw new ErrorCodeException(CodeEnum.AFFILIATE_CHANNEL_EXISTS);
        }
        AffiliateOutreachChannel channel = new AffiliateOutreachChannel();
        channel.setId(IdUtil.simpleUUID())
                .setBrandId(brandId)
                .setChannelName(outreachName)
                .setViews(0)
                .setSales(BigDecimal.ZERO)
                .setCellSaleVolume(0)
                .setPlanSaleVolume(0)
                .setRefundOrders(0)
                .setModifiedAt(new Date())
                .setCreateAt(new Date());
        affiliateOutreachChannelMapper.insert(channel);
    }

    @Override
    public IPage<FetchOutreachChannelPageRO> findOutreachChannelRecord(FetchOutreachChannelPageDTO dto) {
        IPage<FetchOutreachChannelPageRO> page  = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
       return affiliateOutreachChannelMapper.selectPageByDTO(page,dto);
    }
}
