package com.norm.timemall.app.base.controller;

import cn.hutool.core.lang.Validator;

import cn.hutool.core.util.ObjectUtil;
import com.norm.timemall.app.base.entity.Account;
import com.norm.timemall.app.base.entity.PasswordResetDTO;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.handlers.DelAccountHandler;
import com.norm.timemall.app.base.handlers.PasswordResetHandler;
import com.norm.timemall.app.base.handlers.VerificationCodeHandler;
import com.norm.timemall.app.base.entity.JoinDTO;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.util.IpLocationUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Autowired
    private DelAccountHandler delAccountHandler;

    @Resource
    private AuthenticationManager authenticationManager;

    /*
      *通过邮箱进行注册
     */
    @PostMapping(value = "/api/v1/web_mall/email_or_phone_join")
    public  SuccessVO doSignUp(@Validated JoinDTO dto)
    {

        // check verified code
        boolean verify = verificationCodeHandler.uniVerify(dto.getEmailOrPhone(), dto.getQrcode());
        if(!verify){
            throw new ErrorCodeException(CodeEnum.INVALID_QRCODE);
        }
        // sign up
        accountService.doUniSignUp(dto.getEmailOrPhone(), dto.getPassword());
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /*
      * 账号注销
     */
    @RequestMapping(value = "/api/v1/web_mall/account_delete", method = RequestMethod.DELETE)
    public  SuccessVO deleteAccount(@AuthenticationPrincipal CustomizeUser userDetails)
    {
        delAccountHandler.delAccountProcess();
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /*
     *发送注册验证码
     */
    @PostMapping(value = "/api/v1/web_mall/signup/qrcode")
    public  SuccessVO sendVerificationCodeOfSignup(@RequestParam String emailOrPhone, HttpServletRequest request) throws Exception {

        boolean isMobile = Validator.isMobile(emailOrPhone);
        boolean isEmail = Validator.isEmail(emailOrPhone);
        if(!(isEmail || isMobile)){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_DISABLE);
        }
        // check emailOrPhone
        Account targetAccount = accountService.findAccountByUserName(emailOrPhone);
        if(ObjectUtil.isNotNull(targetAccount)){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_ALREADY_EXIST);
        }
        // send verification code
        if(isEmail){
            verificationCodeHandler.doSendEmailCode(emailOrPhone);
        }
        if(isMobile){
            String ipAddress = IpLocationUtil.getIpAddress(request);
            verificationCodeHandler.doSendPhoneCode(emailOrPhone,ipAddress);
        }


        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /*
     *发送密码重置验证码
     */
    @PostMapping(value = "/api/v1/web_mall/password_reset/qrcode")
    public  SuccessVO sendPasswordResetCode(@RequestParam String emailOrPhone, HttpServletRequest request) throws Exception {
        boolean isMobile = Validator.isMobile(emailOrPhone);
        boolean isEmail = Validator.isEmail(emailOrPhone);
        if(!(isEmail || isMobile)){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_DISABLE);
        }

        // check emailOrPhone
        Account targetAccount = accountService.findAccountByUserName(emailOrPhone);
        if(ObjectUtil.isNull(targetAccount)){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        // send verification code

        if(isEmail){
            passwordResetHandler.doSendEmailOfPasswordReset(emailOrPhone);
        }
        if(isMobile){
            String ipAddress = IpLocationUtil.getIpAddress(request);
            passwordResetHandler.doSendPhoneCodeOfPasswordReset(emailOrPhone,ipAddress);
        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /*
     *密码重置
     */
    @PostMapping(value = "/api/v1/web_mall/do_password_reset")
    public  SuccessVO passwordReset(@Validated PasswordResetDTO dto)
    {

        boolean isMobile = Validator.isMobile(dto.getEmailOrPhone());
        boolean isEmail = Validator.isEmail(dto.getEmailOrPhone());
        if(!(isEmail || isMobile)){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_DISABLE);
        }

        // uni verify
        boolean checkQrcode = passwordResetHandler.uniVerifyForPasswordReset(dto.getEmailOrPhone(),dto.getQrcode(), isMobile,isEmail);
        if(!checkQrcode){
            throw new ErrorCodeException(CodeEnum.INVALID_QRCODE);
        }

        if(isEmail){
            passwordResetHandler.doEmailPasswordReset(dto);
        }
        if(isMobile){
            passwordResetHandler.doPhonePasswordReset(dto);
        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }


}
