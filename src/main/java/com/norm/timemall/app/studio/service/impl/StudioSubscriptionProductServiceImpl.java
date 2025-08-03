package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.SubsOffer;
import com.norm.timemall.app.base.mo.SubsPlan;
import com.norm.timemall.app.base.mo.SubsProduct;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubsProductPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioSubsProductChangeDTO;
import com.norm.timemall.app.studio.domain.dto.StudioSubsProductCreateDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsProductPageRO;
import com.norm.timemall.app.studio.mapper.StudioSubsOfferMapper;
import com.norm.timemall.app.studio.mapper.StudioSubsPlanMapper;
import com.norm.timemall.app.studio.mapper.StudioSubsProductMapper;
import com.norm.timemall.app.studio.service.StudioSubscriptionProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioSubscriptionProductServiceImpl implements StudioSubscriptionProductService {
    @Autowired
    private StudioSubsProductMapper studioSubsProductMapper;
    @Autowired
    private StudioSubsPlanMapper studioSubsPlanMapper;
    @Autowired
    private StudioSubsOfferMapper studioSubsOfferMapper;

    @Override
    public void newProduct(StudioSubsProductCreateDTO dto) {
       // query product code
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<SubsProduct> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(SubsProduct::getSellerBrandId,sellerBrandId)
                .eq(SubsProduct::getProductCode,dto.getProductCode());

        SubsProduct subsProduct = studioSubsProductMapper.selectOne(wrapper);
        if(ObjectUtil.isNotNull(subsProduct)){
            throw new QuickMessageException("产品编码已注册，操作失败");
        }
        SubsProduct newProduct=new SubsProduct();
        newProduct.setId(IdUtil.simpleUUID())
                .setProductCode(dto.getProductCode())
                .setProductName(dto.getProductName())
                .setProductDesc(dto.getProductDesc())
                .setSellerBrandId(sellerBrandId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioSubsProductMapper.insert(newProduct);

    }

    @Override
    public IPage<StudioGetSubsProductPageRO> findProducts(StudioGetSubsProductPageDTO dto) {

        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Page<StudioGetSubsProductPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return studioSubsProductMapper.selectPageByQ(page,dto,sellerBrandId);

    }

    @Override
    public void modifyProduct(StudioSubsProductChangeDTO dto) {
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        SubsProduct targetProduct = studioSubsProductMapper.selectById(dto.getProductId());
        if(ObjectUtil.isNull(targetProduct)){
            throw new QuickMessageException("未找到相关商品，操作失败");
        }
        if(!sellerBrandId.equals(targetProduct.getSellerBrandId())){
            throw new QuickMessageException("商品授权不通过，操作失败");
        }

        // query product code

        LambdaQueryWrapper<SubsProduct> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(SubsProduct::getSellerBrandId,sellerBrandId)
                .eq(SubsProduct::getProductCode,dto.getProductCode());

        SubsProduct productCodeCheck = studioSubsProductMapper.selectOne(wrapper);

        if(ObjectUtil.isNotNull(productCodeCheck)&&!dto.getProductId().equals(productCodeCheck.getId())){
            throw new QuickMessageException("产品编码已注册，操作失败");
        }

        SubsProduct product=new SubsProduct();
        product.setId(dto.getProductId())
                .setSellerBrandId(sellerBrandId)
                .setProductName(dto.getProductName())
                .setProductCode(dto.getProductCode())
                .setProductDesc(dto.getProductDesc())
                .setModifiedAt(new Date());
        LambdaUpdateWrapper<SubsProduct> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(SubsProduct::getId,dto.getProductId());
        studioSubsProductMapper.update(product,updateWrapper);

    }

    @Override
    public void delProduct(String id) {

        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        SubsProduct targetProduct = studioSubsProductMapper.selectById(id);
        if(ObjectUtil.isNull(targetProduct)){
            throw new QuickMessageException("未找到相关商品，操作失败");
        }
        if(!sellerBrandId.equals(targetProduct.getSellerBrandId())){
            throw new QuickMessageException("商品授权不通过，操作失败");
        }

        // if exist plan ,not delete
        LambdaQueryWrapper<SubsPlan> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(SubsPlan::getProductId,id);
        boolean existsPlanUse = studioSubsPlanMapper.exists(wrapper);
        if(existsPlanUse){
            throw new QuickMessageException("商品名下有套餐，操作失败");
        }

        // if exists offer ,not delete
        LambdaQueryWrapper<SubsOffer> offerLambdaQueryWrapper=Wrappers.lambdaQuery();
        offerLambdaQueryWrapper.eq(SubsOffer::getForProductId,id);
        boolean existsOfferUse = studioSubsOfferMapper.exists(offerLambdaQueryWrapper);
        if(existsOfferUse){
            throw new QuickMessageException("商品名下有优惠配置，操作失败");
        }

        studioSubsProductMapper.deleteById(id);
    }
}
