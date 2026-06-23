package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.mall.domain.dto.FeedbackDTO;
import com.norm.timemall.app.mall.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeedbackController {
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private FeedBackService feedBackService;
    @ResponseBody
    @PostMapping("/api/v1/web_mall/feedback")
    public SuccessVO userFeedback(@Validated FeedbackDTO dto){

        // 存储图片
        String attachmentUri="";
        if( dto.getAttachment()!=null && !dto.getAttachment().isEmpty()){
            attachmentUri = fileStoreService.storeWithLimitedAccess(dto.getAttachment(), FileStoreDir.FEEDBACK);
        }
        feedBackService.newFeedback(dto.getIssue(),dto.getContactInfo(),attachmentUri);
        return  new SuccessVO(CodeEnum.SUCCESS);

    }
}
