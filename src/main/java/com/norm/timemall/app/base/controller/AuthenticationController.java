package com.norm.timemall.app.base.controller;

import com.norm.timemall.app.base.entity.EmailJoinDTO;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * user related action
 */
@RestController
public class AuthenticationController {

    @Autowired
    private AccountService accountService;

    /*
      *通过邮箱进行注册
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/web_mall/email_join")
    public  SuccessVO doSignUpByEmail(@Validated EmailJoinDTO dto)
    {
        accountService.doSignUpWithEmail(dto.getEmail(), dto.getPassword());
        // todo 发送激活邮件
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /*
      * 账号注销
     */
    @RequestMapping(value = "/api/v1/web_mall/account_delete", method = RequestMethod.DELETE)
    public  SuccessVO deleteAccount(@AuthenticationPrincipal CustomizeUser userDetails)
    {
        accountService.deleteAccount(userDetails);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
