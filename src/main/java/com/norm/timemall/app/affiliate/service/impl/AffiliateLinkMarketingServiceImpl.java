package com.norm.timemall.app.affiliate.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.affiliate.domain.dto.FetchLinkMarketingPageDTO;
import com.norm.timemall.app.affiliate.domain.dto.NewLinkMarketingDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchLinkMarketingPageRO;
import com.norm.timemall.app.affiliate.mapper.AffiliateInfluencerProductMapper;
import com.norm.timemall.app.affiliate.mapper.AffiliateLinkMarketingMapper;
import com.norm.timemall.app.affiliate.service.AffiliateLinkMarketingService;
import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AffiliateInfluencerProduct;
import com.norm.timemall.app.base.mo.AffiliateLinkMarketing;
import com.norm.timemall.app.base.util.ShortLinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AffiliateLinkMarketingServiceImpl implements AffiliateLinkMarketingService {
    @Autowired
    private AffiliateLinkMarketingMapper affiliateLinkMarketingMapper;
    @Autowired
    private AffiliateInfluencerProductMapper affiliateInfluencerProductMapper;

    @Autowired
    private EnvBean envBean;
    @Override
    public void deleteLinkRecord(String linkMarketingId) {
        // todo short link
        LambdaQueryWrapper<AffiliateLinkMarketing> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(AffiliateLinkMarketing::getId,linkMarketingId)
                        .eq(AffiliateLinkMarketing::getBrandId, SecurityUserHelper.getCurrentPrincipal().getBrandId());
        affiliateLinkMarketingMapper.delete(wrapper);
    }

    @Override
    public IPage<FetchLinkMarketingPageRO> findLinkMarketingRecord(FetchLinkMarketingPageDTO dto) {

        IPage<FetchLinkMarketingPageRO> page  = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return affiliateLinkMarketingMapper.selectPageByDTO(page,SecurityUserHelper.getCurrentPrincipal().getBrandId(), dto);
    }

    @Override
    public void addOneLinkMarketingRecord(NewLinkMarketingDTO dto) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // validate product and channel
        LambdaQueryWrapper<AffiliateLinkMarketing> afQueryWrapper=Wrappers.lambdaQuery();
        afQueryWrapper.eq(AffiliateLinkMarketing::getCellId,dto.getCellId())
                .eq(AffiliateLinkMarketing::getOutreachChannelId,dto.getOutreachChannelId());
        boolean exists = affiliateLinkMarketingMapper.exists(afQueryWrapper);
        if(exists){
            throw new ErrorCodeException(CodeEnum.AFFILIATE_LINKS_EXIST);
        }

        // query product info
        LambdaQueryWrapper<AffiliateInfluencerProduct> influencerProductWrapper=Wrappers.lambdaQuery();
        influencerProductWrapper.eq(AffiliateInfluencerProduct::getCellId,dto.getCellId())
                .eq(AffiliateInfluencerProduct::getBrandId,brandId);
        AffiliateInfluencerProduct product = affiliateInfluencerProductMapper.selectOne(influencerProductWrapper);
        if(product==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // product detail example: https://www.bluvarri.com/mall/cell-detail?cell_id=ceb877196cd141d5901e4fd18103357a&brand_id=73fb64fc5116408eb5d4e0afd045e105
        String longUrl= envBean.getWebsite() + "mall/cell-detail?cell_id=" + product.getCellId() + "&brand_id=" + product.getBrandId()
                + "&influencer=" + brandId+ "&chn="+dto.getOutreachChannelId()+"&market=link";
        String shortLik= envBean.getShortSite() + ShortLinkUtil.shortUrl(longUrl)[0] + RandomUtil.randomStringUpper(1);

        AffiliateLinkMarketing linkMarketing = new AffiliateLinkMarketing();
        linkMarketing.setId(IdUtil.simpleUUID())
                .setSupplierBrandId(product.getSupplierBrandId())
                .setBrandId(brandId)
                .setCellId(dto.getCellId())
                .setOutreachChannelId(dto.getOutreachChannelId())
                .setCellSaleVolume(0)
                .setPlanSaleVolume(0)
                .setViews(0)
                .setSales(BigDecimal.ZERO)
                .setRefundOrders(0)
                .setUnsettledPayment(BigDecimal.ZERO)
                .setSettledPayment(BigDecimal.ZERO)
                .setShortlink(shortLik)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        affiliateLinkMarketingMapper.insert(linkMarketing);

    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        LambdaQueryWrapper<AffiliateLinkMarketing> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(AffiliateLinkMarketing::getShortlink,envBean.getShortSite()+shortUrl);
        AffiliateLinkMarketing linkMarketing = affiliateLinkMarketingMapper.selectOne(wrapper);
        if(linkMarketing==null){
            return "/404.html";
        }
        return envBean.getWebsite() + "mall/cell-detail?cell_id=" + linkMarketing.getCellId() + "&brand_id=" + linkMarketing.getSupplierBrandId()
                + "&influencer=" + linkMarketing.getBrandId()+ "&chn="+linkMarketing.getOutreachChannelId()+"&market=link";
    }
}
