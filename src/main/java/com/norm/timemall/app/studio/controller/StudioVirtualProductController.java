package com.norm.timemall.app.studio.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.VirtualProduct;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.vo.FetchVirtualProductMetaInfoVO;
import com.norm.timemall.app.studio.domain.vo.StudioVirtualProductCreateVO;
import com.norm.timemall.app.studio.service.StudioVirtualProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class StudioVirtualProductController {

    @Autowired
    private StudioVirtualProductService studioVirtualProductService;

    @Autowired
    private FileStoreService fileStoreService;


    @PostMapping("/api/v1/web_estudio/virtual/product/create")
    public StudioVirtualProductCreateVO createProduct(@Validated StudioVirtualProductCreateDTO dto) throws IOException {

        // validate file
        if(dto.getThumbnail() == null || dto.getThumbnail().isEmpty()){
            throw new QuickMessageException("未提供主图材料，操作失败");
        }
        String fileType= FileTypeUtil.getType(dto.getThumbnail().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store thumbnail file to oss
        String  thumbnailUrl = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getThumbnail(), FileStoreDir.VIRTUAL_PRODUCT_THUMBNAIL);

        String productId = studioVirtualProductService.newProduct(dto,thumbnailUrl);
        StudioVirtualProductCreateVO vo = new StudioVirtualProductCreateVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setProductId(productId);

        return vo;

    }

    @PutMapping("/api/v1/web_estudio/virtual/product/change")
    public SuccessVO changeProduct(@RequestBody @Validated StudioVirtualProductChangeDTO dto){

        studioVirtualProductService.changeProductMetaInfo(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/web_estudio/virtual/product/change_desc")
    public SuccessVO changeProductDesc(@RequestBody @Validated StudioVirtualProductChangeDescDTO dto){

        studioVirtualProductService.changeProductDescInfo(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/web_estudio/virtual/product/change_thumbnail")
    public SuccessVO changeThumbnail(@Validated StudioVirtualProductChangeThumbnailDTO dto) throws IOException {

        // validate file
        if(dto.getThumbnail() == null || dto.getThumbnail().isEmpty()){
            throw new QuickMessageException("未提供主图材料，操作失败");
        }
        String fileType= FileTypeUtil.getType(dto.getThumbnail().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        VirtualProduct targetProduct = studioVirtualProductService.findOneProduct(dto.getProductId());
        // validate role
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        boolean isSeller = ObjectUtil.isNotNull(targetProduct) && sellerBrandId.equals(targetProduct.getSellerBrandId());
        if(!isSeller){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }


        // store thumbnail file to oss
        String  thumbnailUrl = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getThumbnail(), FileStoreDir.VIRTUAL_PRODUCT_THUMBNAIL);

       // db
        studioVirtualProductService.changeProductThumbnail(dto.getProductId(),thumbnailUrl);

        // delete old data from oss
        fileStoreService.deleteImageAndAvifFile(targetProduct.getThumbnailUrl());

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @GetMapping("/api/v1/web_estudio/brand/virtual/product/{id}/meta")
    public FetchVirtualProductMetaInfoVO fetchVirtualProductMetaInfo(@PathVariable("id") String productId){

        return studioVirtualProductService.findProductMetaInfo(productId);

    }

    @PutMapping("/api/v1/web_estudio/virtual/product/status")
    public SuccessVO productStatusManagement(@RequestBody @Validated StudioVirtualProductStatusManagementDTO dto){

        studioVirtualProductService.modifyProductStatus(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PostMapping("/api/v1/web_estudio/virtual/product/shipping/setting")
    public SuccessVO shippingSetting(@Validated @RequestBody StudioVrProductShippingSettingDTO dto){

        studioVirtualProductService.modifyShippingInfo(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @DeleteMapping("/api/v1/web_estudio/virtual/product/{id}/remove")
    public SuccessVO removeProduct(@PathVariable("id") String productId){

        studioVirtualProductService.removeOneProduct(productId);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/web_estudio/virtual/product/deliver/change")
    public SuccessVO changeDeliverMaterial(@Validated StudioVirtualProductChangeDeliverMaterialDTO dto){


        boolean needStoreFile = !(dto.getDeliverAttachment() == null || dto.getDeliverAttachment().isEmpty());

        VirtualProduct targetProduct = studioVirtualProductService.findOneProduct(dto.getProductId());
        // validate role
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        boolean isSeller = ObjectUtil.isNotNull(targetProduct) && sellerBrandId.equals(targetProduct.getSellerBrandId());
        if(!isSeller){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // store deliver file to oss
        String  deliverAttachmentUrl ="";
        if(needStoreFile){
              deliverAttachmentUrl = fileStoreService.storeWithLimitedAccess(dto.getDeliverAttachment(), FileStoreDir.VIRTUAL_PRODUCT_DELIVER);
        }

        // update db
        studioVirtualProductService.modifyProductDeliverMaterial(dto,deliverAttachmentUrl);

        // delete old data from oss
        fileStoreService.deleteFile(targetProduct.getDeliverAttachment());

        return new SuccessVO(CodeEnum.SUCCESS);

    }



}
