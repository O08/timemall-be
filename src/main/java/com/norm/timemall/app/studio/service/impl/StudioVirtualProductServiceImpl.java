package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.ProductStatusEnum;
import com.norm.timemall.app.base.enums.VirtualProductShippingMethodEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.VirtualProduct;
import com.norm.timemall.app.base.mo.VirtualProductRandomItem;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.ro.FetchVirtualProductMetaInfoRO;
import com.norm.timemall.app.studio.domain.vo.FetchVirtualProductMetaInfoVO;
import com.norm.timemall.app.studio.mapper.StudioVirtualProductMapper;
import com.norm.timemall.app.studio.mapper.StudioVirtualProductRandomItemMapper;
import com.norm.timemall.app.studio.service.StudioVirtualProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;


@Service
public class StudioVirtualProductServiceImpl implements StudioVirtualProductService {
    @Autowired
    private StudioVirtualProductMapper studioVirtualProductMapper;

    @Autowired
    private StudioVirtualProductRandomItemMapper studioVirtualProductRandomItemMapper;



    @Override
    public FetchVirtualProductMetaInfoVO findProductMetaInfo(String productId) {

        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        FetchVirtualProductMetaInfoRO product = studioVirtualProductMapper.selectProductMetaById(productId,sellerBrandId);
        FetchVirtualProductMetaInfoVO vo = new FetchVirtualProductMetaInfoVO();
        vo.setProduct(product==null
                ? new FetchVirtualProductMetaInfoRO().setShowcase(new ArrayList<>()).setTags(new ArrayList<>())
                : product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @Override
    public void modifyProductStatus(StudioVirtualProductStatusManagementDTO dto) {

        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        VirtualProduct product = studioVirtualProductMapper.selectById(dto.getProductId());

        // can't modify to draft
        if(ProductStatusEnum.DRAFT.getMark().equals(dto.getStatus())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // only seller can change product status
        if(product==null){
            throw new QuickMessageException("未找到相关商品数据");
        }
        if(!sellerBrandId.equals(product.getSellerBrandId())){
            throw new QuickMessageException("非法访问");
        }
        // if product deliver material not set, block online action
        if(ProductStatusEnum.ONLINE.getMark().equals(dto.getStatus()) && CharSequenceUtil.isBlank(product.getDeliverNote())){
            throw new QuickMessageException("未提供《交付说明书》资料,操作失败");
        }
        // if product desc  not set, block online action
        if(ProductStatusEnum.ONLINE.getMark().equals(dto.getStatus()) && CharSequenceUtil.isBlank(product.getProductDesc())){
            throw new QuickMessageException("未提供《关于商品》资料,操作失败");
        }
        product.setProductStatus(dto.getStatus());
        product.setModifiedAt(new Date());
        studioVirtualProductMapper.updateById(product);

    }

    @Override
    public void removeOneProduct(String productId) {
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        VirtualProduct product = studioVirtualProductMapper.selectById(productId);

        // only seller can delete product  and only support delete draft status product
        boolean isSeller = ObjectUtil.isNotNull(product) &&  sellerBrandId.equals(product.getSellerBrandId());
        boolean isDraftStatus = ObjectUtil.isNotNull(product) && ProductStatusEnum.DRAFT.getMark().equals(product.getProductStatus());

        if(!(isSeller && isDraftStatus)){
            throw new QuickMessageException("仅支持删除卖家名下草稿箱中的产品,操作失败");
        }

        studioVirtualProductMapper.deleteById(productId);


    }

    @Override
    public String newProduct(StudioVirtualProductCreateDTO dto, String thumbnailUrl) {
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // validate tags to json  arr
        try {
            new JSONArray(dto.getTags());
        } catch (JSONException ne) {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        VirtualProduct vrProduct = new VirtualProduct();
        String productId = IdUtil.simpleUUID();
        vrProduct
                .setId(productId)
                .setProductName(dto.getProductName())
                .setProductPrice(dto.getProductPrice())
                .setProvideInvoice(dto.getProvideInvoice())
                .setInventory(dto.getInventory())
                .setThumbnailUrl(thumbnailUrl)
                .setProductStatus(ProductStatusEnum.DRAFT.getMark())
                .setSellerBrandId(sellerBrandId)
                .setTags(dto.getTags())
                .setShippingMethod(VirtualProductShippingMethodEnum.MANUAL.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioVirtualProductMapper.insert(vrProduct);

        return  productId;

    }

    @Override
    public void changeProductMetaInfo(StudioVirtualProductChangeDTO dto) {

        // validate tags to json  arr
        try {
            new JSONArray(dto.getTags());
        } catch (JSONException ne) {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // validated product and role
        VirtualProduct product = studioVirtualProductMapper.selectById(dto.getProductId());
        if(product==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller =  sellerBrandId.equals(product.getSellerBrandId());

        if(!isSeller){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // change data
        product.setProductName(dto.getProductName())
                .setProductPrice(dto.getProductPrice())
                .setProvideInvoice(dto.getProvideInvoice())
                .setTags(dto.getTags())
                .setInventory(dto.getInventory())
                .setModifiedAt(new Date())
                ;
        studioVirtualProductMapper.updateById(product);

    }

    @Override
    public VirtualProduct findOneProduct(String productId) {

        return studioVirtualProductMapper.selectById(productId);

    }

    @Override
    public void changeProductThumbnail(String productId, String thumbnailUrl) {

        VirtualProduct vrProduct= new VirtualProduct();
        vrProduct.setId(productId)
                .setThumbnailUrl(thumbnailUrl)
                .setModifiedAt(new Date());

        studioVirtualProductMapper.updateById(vrProduct);

    }

    @Override
    public void modifyProductDeliverMaterial(StudioVirtualProductChangeDeliverMaterialDTO dto, String deliverAttachmentUrl) {

        VirtualProduct vrProduct= new VirtualProduct();
        vrProduct.setId(dto.getProductId())
                .setDeliverNote(dto.getDeliverNote())
                .setModifiedAt(new Date());

        if(CharSequenceUtil.isNotBlank(deliverAttachmentUrl)){
            vrProduct.setDeliverAttachment(deliverAttachmentUrl);
        }

        studioVirtualProductMapper.updateById(vrProduct);

    }

    @Override
    public void changeProductDescInfo(StudioVirtualProductChangeDescDTO dto) {

        // validated product and role
        VirtualProduct product = studioVirtualProductMapper.selectById(dto.getProductId());
        if(product==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller =  sellerBrandId.equals(product.getSellerBrandId());

        if(!isSeller){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // change data
        product.setProductDesc(dto.getProductDesc())
                .setModifiedAt(new Date())
        ;
        studioVirtualProductMapper.updateById(product);
    }

    @Override
    public void modifyShippingInfo(StudioVrProductShippingSettingDTO dto) {
        // check dto
        if(VirtualProductShippingMethodEnum.STANDARD.getMark().equals(dto.getShippingMethod()) && CharSequenceUtil.isBlank(dto.getPack())){
            throw new QuickMessageException("标准发货货品为空");
        }
        if(VirtualProductShippingMethodEnum.RANDOM.getMark().equals(dto.getShippingMethod()) && fetchMerchandiseRows(dto.getProductId())<=0){
            throw new QuickMessageException("随机发货货品为空");
        }

        // validated product and role
        VirtualProduct product = studioVirtualProductMapper.selectById(dto.getProductId());
        if(product==null){
            throw new QuickMessageException("未找到相关商品");
        }
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller =  sellerBrandId.equals(product.getSellerBrandId());

        if(!isSeller){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        product.setPack(dto.getPack());
        product.setShippingMethod(dto.getShippingMethod());
        product.setModifiedAt(new Date());

        studioVirtualProductMapper.updateById(product);

    }
    private Long fetchMerchandiseRows(String productId){
        LambdaQueryWrapper<VirtualProductRandomItem> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(VirtualProductRandomItem::getProductId,productId);
        return studioVirtualProductRandomItemMapper.selectCount(wrapper);
    }
}
