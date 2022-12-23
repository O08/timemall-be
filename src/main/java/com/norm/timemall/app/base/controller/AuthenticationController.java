package com.norm.timemall.app.base.controller;

import com.norm.timemall.app.base.entity.PasswordResetDTO;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.handlers.PasswordResetHandler;
import com.norm.timemall.app.base.handlers.VerificationCodeHandler;
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

    @Autowired
    private VerificationCodeHandler verificationCodeHandler;

    @Autowired
    private PasswordResetHandler passwordResetHandler;

    /*
      *通过邮箱进行注册
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/web_mall/email_join")
    public  SuccessVO doSignUpByEmail(@Validated EmailJoinDTO dto)
    {
        // check verified code
        boolean verify = verificationCodeHandler.verify(dto.getEmail(), dto.getQrcode());
        if(!verify){
            throw new ErrorCodeException(CodeEnum.INVALID_QRCODE);
        }
        // sign up
        accountService.doSignUpWithEmail(dto.getEmail(), dto.getPassword());
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

    /*
     *发送邮箱验证码
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/web_mall/send_email_code")
    public  SuccessVO sendEmailCode(@RequestParam String email)
    {
        verificationCodeHandler.doSendEmailCode(email);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /*
     *发送密码重置邮件
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/web_mall/do_send_password_reset_email")
    public  SuccessVO sendPasswordResetEmail(@RequestParam String email)
    {
        passwordResetHandler.doSendEmailOfPasswordReset(email);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /*
     *密码重置
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/web_mall/do_password_reset")
    public  SuccessVO passwordReset(@Validated PasswordResetDTO dto)
    {
        passwordResetHandler.doPasswordReset(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
