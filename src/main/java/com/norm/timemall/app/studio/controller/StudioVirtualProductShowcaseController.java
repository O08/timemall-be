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
import com.norm.timemall.app.base.mo.VirtualProductShowcase;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.studio.domain.dto.StudioVirtualProductShowcaseAddDTO;
import com.norm.timemall.app.studio.domain.dto.StudioVirtualProductShowcaseChangeDTO;
import com.norm.timemall.app.studio.service.StudioVirtualProductService;
import com.norm.timemall.app.studio.service.StudioVirtualProductShowcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class StudioVirtualProductShowcaseController {

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private StudioVirtualProductShowcaseService studioVirtualProductShowcaseService;

    @Autowired
    private StudioVirtualProductService studioVirtualProductService;

    @PostMapping("/api/v1/web_estudio/virtual/product/showcase/new")
    public SuccessVO addOneCase(@Validated StudioVirtualProductShowcaseAddDTO dto) throws IOException {

        // validate file
        if(dto.getShowcase() == null || dto.getShowcase().isEmpty()){
            throw new QuickMessageException("未提供展示材料，操作失败");
        }
        String fileType= FileTypeUtil.getType(dto.getShowcase().getInputStream());
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
        // max showcase no is 4
        Long showcaseOd = studioVirtualProductShowcaseService.findMaxShowCaseOd(dto.getProductId());
        Long maxShowcaseOd=4L;

        if(showcaseOd >= maxShowcaseOd){
            throw new QuickMessageException("商品展示图例已达最大数量4");
        }

        // store showcaseUrl file to oss
        String  showcaseUrl = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getShowcase(), FileStoreDir.VIRTUAL_PRODUCT_SHOWCASE);

        Long nextShowcaseOd= showcaseOd + 1;
        studioVirtualProductShowcaseService.newShowcase(dto.getProductId(),showcaseUrl,nextShowcaseOd);

        return  new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/web_estudio/virtual/product/showcase/change")
    public SuccessVO changeShowCase(@Validated StudioVirtualProductShowcaseChangeDTO dto) throws IOException {

        // validate file
        if(dto.getShowcase() == null || dto.getShowcase().isEmpty()){
            throw new QuickMessageException("未提供展示材料，操作失败");
        }
        String fileType= FileTypeUtil.getType(dto.getShowcase().getInputStream());
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

        VirtualProductShowcase targetShowcase= studioVirtualProductShowcaseService.findOneCase(dto.getShowcaseId());

        if(targetShowcase == null || !dto.getProductId().equals(targetShowcase.getProductId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // store showcaseUrl file to oss
        String  showcaseUrl = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getShowcase(), FileStoreDir.VIRTUAL_PRODUCT_SHOWCASE);

        // update db
        studioVirtualProductShowcaseService.modifyShowcase(dto.getShowcaseId(),showcaseUrl);

        // delete old file from oss
        fileStoreService.deleteImageAndAvifFile(targetShowcase.getShowcaseUrl());

        return  new SuccessVO(CodeEnum.SUCCESS);

    }
}
