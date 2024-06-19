package com.norm.timemall.app.affiliate.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.affiliate.domain.dto.FetchInfluencerProductPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchInfluencerProductRO;
import com.norm.timemall.app.affiliate.mapper.AffiliateInfluencerProductMapper;
import com.norm.timemall.app.affiliate.mapper.AffiliateLinkMarketingMapper;
import com.norm.timemall.app.affiliate.mapper.AffiliateProductIndMapper;
import com.norm.timemall.app.affiliate.service.AffiliateInfluencerProductSercie;
import com.norm.timemall.app.base.enums.CellMarkEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AffiliateInfluencerProduct;
import com.norm.timemall.app.base.mo.AffiliateLinkMarketing;
import com.norm.timemall.app.base.mo.AffiliateProductInd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AffiliateInfluencerProductSercieImpl implements AffiliateInfluencerProductSercie {
    @Autowired
    private AffiliateInfluencerProductMapper affiliateInfluencerProductMapper;
    @Autowired
    private AffiliateProductIndMapper affiliateProductIndMapper;

    @Autowired
    private AffiliateLinkMarketingMapper affiliateLinkMarketingMapper;
    @Override
    public void newChoice(String cellId) {
        // query raw data
        LambdaQueryWrapper<AffiliateProductInd> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AffiliateProductInd::getCellId,cellId);
        queryWrapper.eq(AffiliateProductInd::getCellMark, CellMarkEnum.ONLINE.getMark());
        AffiliateProductInd baseProduct = affiliateProductIndMapper.selectOne(queryWrapper);
        if(baseProduct==null){
            throw  new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(SecurityUserHelper.getCurrentPrincipal().getBrandId().equals(baseProduct.getSupplierBrandId())){
            throw  new ErrorCodeException(CodeEnum.AFFILIATE_BRAND_SELF_PRODUCT);
        }

        AffiliateInfluencerProduct product=new AffiliateInfluencerProduct();
        product.setId(IdUtil.simpleUUID())
                .setCellId(cellId)
                .setBrandId(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                .setPlanId(baseProduct.getPlanId())
                .setRevshare(baseProduct.getRevshare())
                .setSales(BigDecimal.ZERO)
                .setSupplierBrandId(baseProduct.getSupplierBrandId())
                .setViews(0)
                .setRefundOrders(0)
                .setPlanSaleVolume(0)
                .setPlanPrice(baseProduct.getPlanPrice())
                .setRefundOrders(0)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        affiliateInfluencerProductMapper.insert(product);
    }

    @Override
    public IPage<FetchInfluencerProductRO> findChoiceRecord(FetchInfluencerProductPageDTO dto) {

        IPage<FetchInfluencerProductRO> page  = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        String brandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        dto.setBrandId(brandId);
        return affiliateInfluencerProductMapper.selectPageByDTO(page,dto);

    }

    @Override
    public void delChoiceRecord(String cellId) {
        // validate
        LambdaQueryWrapper<AffiliateLinkMarketing> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(AffiliateLinkMarketing::getCellId,cellId);
        Long l = affiliateLinkMarketingMapper.selectCount(wrapper);
        if(l>0L){
            throw new ErrorCodeException(CodeEnum.AFFILIATE_LINKS_EXIST);
        }
        LambdaQueryWrapper<AffiliateInfluencerProduct> delWrapper=Wrappers.lambdaQuery();
        delWrapper.eq(AffiliateInfluencerProduct::getCellId,cellId);
        affiliateInfluencerProductMapper.delete(delWrapper);

    }
}
