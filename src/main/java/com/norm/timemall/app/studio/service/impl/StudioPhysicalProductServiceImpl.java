package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.ProductStatusEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.PhysicalProduct;
import cn.hutool.core.util.ObjectUtil;
import com.norm.timemall.app.studio.domain.dto.StudioChangePhysicalProductStatusDTO;
import com.norm.timemall.app.studio.domain.dto.StudioCreatePhysicalProductDTO;
import com.norm.timemall.app.studio.domain.dto.StudioEditPhysicalProductDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchPhysicalProductPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalProductPageRO;
import com.norm.timemall.app.studio.mapper.StudioPhysicalProductMapper;
import com.norm.timemall.app.studio.service.StudioPhysicalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.IdUtil;
import java.util.Date;

@Service
public class StudioPhysicalProductServiceImpl implements StudioPhysicalProductService {
    @Autowired
    private StudioPhysicalProductMapper physicalProductMapper;

    @Override
    public void newProduct(StudioCreatePhysicalProductDTO dto, String thumbnailUrl) {
        String sellerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        LambdaQueryWrapper<PhysicalProduct> productCodeWrapper = new LambdaQueryWrapper<>();
        productCodeWrapper.eq(PhysicalProduct::getSellerBrandId, sellerBrandId)
                .eq(PhysicalProduct::getProductCode, dto.getProductCode());
        if (physicalProductMapper.selectCount(productCodeWrapper) > 0) {
            throw new QuickMessageException("商品编码已存在，请勿重复创建");
        }

        Date now = new Date();
        PhysicalProduct entity = new PhysicalProduct();
        entity.setId(IdUtil.simpleUUID());
        entity.setSellerBrandId(sellerBrandId);
        entity.setThumbnail(thumbnailUrl);
        entity.setProductCode(dto.getProductCode());
        entity.setProductName(dto.getProductName());
        entity.setProductDesc(dto.getProductDesc());
        entity.setPrice(dto.getPrice());
        entity.setInventory(dto.getInventory());
        entity.setSoldOrders(0);
        entity.setWarmReminder(dto.getWarmReminder());
        entity.setStatus(ProductStatusEnum.DRAFT.getMark());
        entity.setCreateAt(now);
        entity.setModifiedAt(now);
        physicalProductMapper.insert(entity);
    }

    @Override
    public IPage<StudioFetchPhysicalProductPageRO> fetchProductPage(StudioFetchPhysicalProductPageDTO dto) {
        String sellerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Page<StudioFetchPhysicalProductPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return physicalProductMapper.selectPageByBrandAndQ(page, sellerBrandId, dto);
    }

    @Override
    public PhysicalProduct findOneProduct(String productId) {
        return physicalProductMapper.selectById(productId);
    }

    @Override
    public void editProductInfo(StudioEditPhysicalProductDTO dto) {
        PhysicalProduct product = physicalProductMapper.selectById(dto.getProductId());
        if (product == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        String sellerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if (!sellerBrandId.equals(product.getSellerBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // 售卖中的商品禁止编辑
        if (ProductStatusEnum.ONLINE.getMark().equals(product.getStatus())) {
            throw new QuickMessageException("商品售卖中，不可编辑");
        }

        // validate productCode uniqueness within the same seller (excluding self)
        if (!dto.getProductCode().equals(product.getProductCode())) {
            LambdaQueryWrapper<PhysicalProduct> codeWrapper = new LambdaQueryWrapper<>();
            codeWrapper.eq(PhysicalProduct::getSellerBrandId, sellerBrandId)
                    .eq(PhysicalProduct::getProductCode, dto.getProductCode());
            if (physicalProductMapper.selectCount(codeWrapper) > 0) {
                throw new QuickMessageException("商品编码已存在，请勿重复");
            }
        }

        product.setProductCode(dto.getProductCode())
                .setProductName(dto.getProductName())
                .setProductDesc(dto.getProductDesc())
                .setWarmReminder(dto.getWarmReminder())
                .setPrice(dto.getPrice())
                .setInventory(dto.getInventory())
                .setModifiedAt(new Date());
        physicalProductMapper.updateById(product);
    }

    @Override
    public String removeProduct(String productId) {
        String sellerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        PhysicalProduct product = physicalProductMapper.selectById(productId);

        if(ObjectUtil.isNull(product)){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        // only seller can delete, and only draft status product is deletable
        boolean isSeller = ObjectUtil.isNotNull(product) && sellerBrandId.equals(product.getSellerBrandId());
        boolean isDraftStatus = ObjectUtil.isNotNull(product) && ProductStatusEnum.DRAFT.getMark().equals(product.getStatus());

        if(!isSeller){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        if(!isDraftStatus){
            throw new QuickMessageException("仅支持删除处于草稿状态的商品");
        }

        physicalProductMapper.deleteById(productId);
        return product.getThumbnail();
    }

    @Override
    public void changeProductStatus(StudioChangePhysicalProductStatusDTO dto) {
        String sellerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        PhysicalProduct product = physicalProductMapper.selectById(dto.getProductId());

        // only seller can change status
        if (product == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        if (!sellerBrandId.equals(product.getSellerBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        if(dto.getStatus().equals(product.getStatus())){
            throw new QuickMessageException("商品状态未变更");
        }

        // 禁止回到草稿状态
        if (ProductStatusEnum.DRAFT.getMark().equals(dto.getStatus())) {
            throw new QuickMessageException("商品已上架，不可回撤为草稿状态");
        }

        // 精准更新：只写 status 与 modified_at 两列，避免覆盖其它字段产生脏数据
        LambdaUpdateWrapper<PhysicalProduct> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PhysicalProduct::getId, dto.getProductId())
                .eq(PhysicalProduct::getSellerBrandId, sellerBrandId)
                .set(PhysicalProduct::getStatus, dto.getStatus())
                .set(PhysicalProduct::getModifiedAt, new Date());
        physicalProductMapper.update(null, updateWrapper);
    }
}
