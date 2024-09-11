package com.norm.timemall.app.base.service;

import com.norm.timemall.app.base.entity.Account;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.pojo.FetchWechatUserInfoBO;
import com.norm.timemall.app.base.security.CustomizeUser;
import org.springframework.security.core.userdetails.UserDetails;


public interface AccountService {
    Account findAccountByUserName(String username);
    boolean doSignUpWithEmail(String username, String password);

    void deleteAccount(CustomizeUser userDetails);

    void modifiedPasswordByUserName(String encryptedPassword, String username);

    Brand findBrandInfoByUserId(String userId);
    Brand findBrandInfoByBrandId(String brandId);
    void modifyAccountMark(String mark,String brandId);

    void doSignUpWithWechatUserInfo(FetchWechatUserInfoBO wechatUserInfo);

    Account findAccountByUnionid(String unionid);

    UserDetails loadUserForWechatLogin(String unionid);
}
