package com.norm.timemall.app.studio.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.PhysicalProduct;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalProductPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchPhysicalProductPageVO;
import com.norm.timemall.app.studio.service.StudioPhysicalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class StudioPhysicalProductController {
    @Autowired
    private StudioPhysicalProductService physicalProductService;

    @Autowired
    private FileStoreService fileStoreService;

    @GetMapping("/api/v1/web_estudio/physical_product/query")
    public StudioFetchPhysicalProductPageVO fetchPhysicalProductPage(StudioFetchPhysicalProductPageDTO dto){
        IPage<StudioFetchPhysicalProductPageRO> product = physicalProductService.fetchProductPage(dto);
        StudioFetchPhysicalProductPageVO vo = new StudioFetchPhysicalProductPageVO();
        vo.setProduct(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @PostMapping("/api/v1/web_estudio/physical_product/create_product")
    public SuccessVO createProduct(@Validated StudioCreatePhysicalProductDTO dto) throws IOException {
        // validate file
        if (dto.getThumbnail() == null || dto.getThumbnail().isEmpty()) {
            throw new QuickMessageException("未提供主图材料，操作失败");
        }
        String fileType = FileTypeUtil.getType(dto.getThumbnail().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e -> e.equals(fileType));
        if (notInExtensions) {
            throw new ErrorCodeException(CodeEnum.FILE_FORMAT_NOT_SUPPORT);
        }
        // store thumbnail file to oss
        String thumbnailUrl = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getThumbnail(), FileStoreDir.PHYSICAL_PRODUCT_THUMBNAIL);

        physicalProductService.newProduct(dto, thumbnailUrl);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/web_estudio/physical_product/thumbnail/change")
    public SuccessVO changeThumbnail(@Validated StudioChangePhysicalProductThumbnailDTO dto) throws IOException {

        // validate file
        if (dto.getThumbnail() == null || dto.getThumbnail().isEmpty()) {
            throw new QuickMessageException("未提供主图材料，操作失败");
        }
        String fileType = FileTypeUtil.getType(dto.getThumbnail().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e -> e.equals(fileType));
        if (notInExtensions) {
            throw new ErrorCodeException(CodeEnum.FILE_FORMAT_NOT_SUPPORT);
        }

        PhysicalProduct targetProduct = physicalProductService.findOneProduct(dto.getProductId());
        // validate role
        String sellerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        boolean isSeller = ObjectUtil.isNotNull(targetProduct) && sellerBrandId.equals(targetProduct.getSellerBrandId());
        if (!isSeller) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // thumbnail is stored in order table, so its uri must stay stable; we replace the file in oss instead of changing the uri
        fileStoreService.replaceImageAndProcessAsAvifWithUnlimitedAccess(dto.getThumbnail(), targetProduct.getThumbnail());

        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/web_estudio/physical_product/edit")
    public SuccessVO editProductInfo(@Validated @RequestBody StudioEditPhysicalProductDTO dto){
        physicalProductService.editProductInfo(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @DeleteMapping("/api/v1/web_estudio/physical_product/{product_id}/remove")
    public SuccessVO removeProduct(@PathVariable("product_id") String productId){

        String thumbnail = physicalProductService.removeProduct(productId);
        fileStoreService.deleteImageAndAvifFile(thumbnail);

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/web_estudio/physical_product/change_status")
    public SuccessVO changeProductStatus(@Validated @RequestBody StudioChangePhysicalProductStatusDTO dto){
        physicalProductService.changeProductStatus(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
