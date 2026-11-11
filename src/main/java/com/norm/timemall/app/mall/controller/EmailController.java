package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.dto.SendEmailNoticeDTO;
import com.norm.timemall.app.mall.service.EmailNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailNoticeService emailNoticeService;
    @ResponseBody
    @PostMapping(value = "/api/v1/web_mall/email_notice")
    public SuccessVO sendEmailNotice(@RequestBody @Validated SendEmailNoticeDTO dto){

        emailNoticeService.sendEmailNotice(dto);

        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
