package com.norm.timemall.app.studio.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.enums.FlierStatusEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Flier;
import com.norm.timemall.app.base.mo.FlierCopy;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.vo.StudioFetchBrandCreatedFlierPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchReceiverFlierPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioFlierVisitorInfoVO;
import com.norm.timemall.app.studio.service.StudioFlierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class StudioFlierController {
    @Autowired
    private StudioFlierService studioFlierService;

    @Autowired
    private FileStoreService fileStoreService;

    @GetMapping("/api/v1/web_estudio/brand/flier/query")
    public StudioFetchBrandCreatedFlierPageVO fetchBrandCreatedFlier(@Validated StudioFetchBrandCreatedFlierPageDTO dto) {
        return studioFlierService.findBrandCreatedFlierPage(dto);
    }

    @PostMapping("/api/v1/web_estudio/brand/flier/new")
    public SuccessVO createFlier(@Validated StudioCreateFlierDTO dto) throws IOException {
        // validate file
        if (dto.getMaterial() == null || dto.getMaterial().isEmpty()) {
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        // validate link url
        if(ObjectUtil.isNotEmpty(dto.getCtaLink()) && !Validator.isUrl(dto.getCtaLink())){
            throw new QuickMessageException("链接格式不正确");
        }
        String fileType = FileTypeUtil.getType(dto.getMaterial().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e -> e.equals(fileType));
        if (notInExtensions) {
            throw new ErrorCodeException(CodeEnum.FILE_FORMAT_NOT_SUPPORT);
        }

        String contentLink = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(
                dto.getMaterial(), FileStoreDir.FLIER_MATERIAL);

        studioFlierService.createFlier(dto, contentLink);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/web_estudio/brand/flier/edit")
    public SuccessVO editFlier(@Validated @RequestBody StudioEditFlierDTO dto){

        if (ObjectUtil.isNotEmpty(dto.getCtaLink()) && !Validator.isUrl(dto.getCtaLink())) {
            throw new QuickMessageException("链接格式不正确");
        }
        studioFlierService.editFlier(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @DeleteMapping("/api/v1/web_estudio/flier/{id}/remove")
    public SuccessVO delFlier(@PathVariable("id") String flierId){
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        Flier existing = studioFlierService.findFlierById(flierId);

        if (existing == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        if (!existing.getAuthorBrandId().equals(brandId)) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        if (!FlierStatusEnum.NORMAL.getValue().equals(existing.getStatus())) {
            throw new QuickMessageException("当前状态不支持删除");
        }
        studioFlierService.removeOneFlier(flierId);

        // 清理 OSS 文件
        fileStoreService.deleteImageAndAvifFile(existing.getContentLink());

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/web_estudio/brand/flier/material/change")
    public SuccessVO changeFlierMaterial(@Validated StudioChangeFlierMaterialDTO dto) throws IOException {
        // validate file
        if (dto.getMaterial() == null || dto.getMaterial().isEmpty()) {
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        String fileType = FileTypeUtil.getType(dto.getMaterial().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e -> e.equals(fileType));
        if (notInExtensions) {
            throw new ErrorCodeException(CodeEnum.FILE_FORMAT_NOT_SUPPORT);
        }
        Flier existing = studioFlierService.findFlierById(dto.getFlierId());

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        if (existing == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        if (!existing.getAuthorBrandId().equals(brandId)) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        if (!FlierStatusEnum.NORMAL.getValue().equals(existing.getStatus())) {
            throw new QuickMessageException("当前状态不支持修改素材");
        }


        String newContentLink = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(
                dto.getMaterial(), FileStoreDir.FLIER_MATERIAL);

        studioFlierService.changeFlierMaterial(dto.getFlierId(), newContentLink);

        // 删除旧 OSS 文件
        fileStoreService.deleteImageAndAvifFile(existing.getContentLink());

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/web_estudio/receiver/flier/query")
    public StudioFetchReceiverFlierPageVO fetchReceiverFlier(@Validated StudioFetchReceiverFlierPageDTO dto){
        return studioFlierService.findReceiverFlierPage(dto);
    }
    @DeleteMapping("/api/v1/web_estudio/flier/copy{id}/remove")
    public SuccessVO delCopyFlier(@PathVariable("id") String flierCopyId){
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        FlierCopy existing = studioFlierService.findFlierCopyById(flierCopyId);

        if (existing == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        if (!existing.getReceiverBrandId().equals(brandId)) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        studioFlierService.removeOneFlierCopy(flierCopyId);

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/open/web_estudio/flier/visitor/info")
    public StudioFlierVisitorInfoVO fetchVisitorInfo(@Validated StudioFlierVisitorInfoDTO dto){
        return studioFlierService.findVisitorFlierInfo(dto);
    }

    @PostMapping("/api/v1/web_estudio/brand/flier/handout")
    public SuccessVO handoutFlier(@Validated @RequestBody StudioHandoutFlierDTO dto){
        studioFlierService.handoutFlier(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PostMapping("/api/v1/web_estudio/flier/interact")
    public SuccessVO interactFlier(@Validated @RequestBody StudioInteractFlierDTO dto){
        studioFlierService.interactFlier(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PostMapping("/api/v1/team/dsp_case/action/flier/{id}/block")
    public SuccessVO blockFlier(@PathVariable("id") String flierId){
        studioFlierService.blockedFlier(flierId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
