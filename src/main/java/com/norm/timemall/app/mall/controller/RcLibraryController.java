package com.norm.timemall.app.mall.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.mall.domain.dto.FetchRcPageDTO;
import com.norm.timemall.app.mall.domain.ro.MallGetRcListRO;
import com.norm.timemall.app.mall.domain.vo.MallGetRcPageVO;
import com.norm.timemall.app.mall.service.BluvarrierService;
import com.norm.timemall.app.mall.service.RcLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RcLibraryController {
    @Autowired
    private RcLibraryService rcLibraryService;

    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private BluvarrierService bluvarrierService;

    @PutMapping("/api/v1/web_mall/upload_rc")
    public SuccessVO uploadResource(@RequestParam("rc") MultipartFile rc,
                                    @RequestParam("thumbnail") MultipartFile thumbnail,
                                    @RequestParam("title") String title,
                                    @RequestParam("preview") MultipartFile preview){
        // validate
        if(!SecurityUserHelper.alreadyLogin()){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(thumbnail.isEmpty()|| rc.isEmpty()|| CharSequenceUtil.isBlank(title) || title.length()>79){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        String operationUserId = bluvarrierService.findBluvarrier().getCustomerId();
        if(operationUserId.equals(SecurityUserHelper.getCurrentPrincipal().getUserId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }


        String rcAddress = fileStoreService.storeWithLimitedAccess(rc, FileStoreDir.RC_PPT_ITEM);
        String previewAddress = preview.isEmpty() ? "" : fileStoreService.storeWithLimitedAccess(preview, FileStoreDir.RC_PPT_PREVIEW);
        String thumbnailAddress = fileStoreService.storeWithLimitedAccess(thumbnail,FileStoreDir.RC_PPT_PREVIEW);
        rcLibraryService.newRc(rcAddress,title,previewAddress,thumbnailAddress);

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @GetMapping("/api/v1/web_mall/resource")
    public MallGetRcPageVO getRcList(FetchRcPageDTO dto){

        IPage<MallGetRcListRO> offer = rcLibraryService.findRcList(dto);
        MallGetRcPageVO vo = new MallGetRcPageVO();
        vo.setOffer(offer);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
