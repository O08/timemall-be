package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseBrandMapper;
import com.norm.timemall.app.base.mapper.FinAccountMapper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.entity.Customer;
import com.norm.timemall.app.base.mapper.CustomerMapper;
import com.norm.timemall.app.base.entity.Account;
import com.norm.timemall.app.base.util.mate.MybatisMateEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private BaseBrandMapper baseBrandMapper;
    @Autowired
    private FinAccountMapper finAccountMapper;
    @Autowired
    private MybatisMateEncryptor mybatisMateEncryptor;
    @Override
    public Account findAccountByUserName(String username) {
        LambdaQueryWrapper<Customer> lambdaQuery = Wrappers.<Customer>lambdaQuery();
        lambdaQuery.eq(Customer::getLoginName, mybatisMateEncryptor.defaultEncrypt(username) );
        Customer customer = customerMapper.selectOne(lambdaQuery);
        Account account = new Account();
        account.setUserId(customer.getId());
        account.setName(customer.getCustomerName());
        account.setPassword(customer.getPassword());
        return account;
    }

    @Override
    public boolean doSignUpWithEmail(String username, String password) {
        // if customer already sign up return
        LambdaQueryWrapper<Customer> lambdaQuery = Wrappers.<Customer>lambdaQuery();
        lambdaQuery.eq(Customer::getLoginName, mybatisMateEncryptor.defaultEncrypt(username) );
        Long cnt = customerMapper.selectCount(lambdaQuery);
        if(cnt > 0){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_ALREADY_EXIST);
         }
        // register account
        String encryptedPassword= new BCryptPasswordEncoder().encode(password);

        Customer customer = new Customer();
        customer.setId(IdUtil.simpleUUID())
                .setCustomerName( StrUtil.subBefore(username,"@",true) )
                .setPassword(encryptedPassword)
                .setNotifyEmail(username)
                .setLoginName(username)
                .setRegistAt(new Date())
                .setModifiedAt(new Date());

        customerMapper.insert(customer);

        // bind a brand for new user
        newBrandWhenUserRegister(customer.getId(),StrUtil.subBefore(username,"@",true));
        return true;
    }

    @Override
    public void deleteAccount(CustomizeUser userDetails) {
        // delete account
        LambdaQueryWrapper<Customer> lambdaQuery = Wrappers.<Customer>lambdaQuery();
        lambdaQuery.eq(Customer::getId, userDetails.getUserId());
        customerMapper.delete(lambdaQuery);
        // todo 数据注销逻辑
        // security logout
        SecurityUserHelper.logout();
    }

    @Override
    public void modifiedPasswordByUserName(String encryptedPassword, String username) {
        Customer customer = new Customer();
        customer.setPassword(encryptedPassword)
                        .setLoginName(username);
        customerMapper.updatePasswordByUserName(customer);

    }

    @Override
    public Brand findBrandInfoByUserId(String userId) {
        LambdaQueryWrapper<Brand> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Brand::getCustomerId,userId);
        Brand brand = baseBrandMapper.selectOne(wrapper);
        return brand;
    }

    @Override
    public Brand findBrandInfoByBrandId(String brandId) {
        return baseBrandMapper.selectById(brandId);
    }

    private void newBrandWhenUserRegister(String userId,String brandName){
        Brand brand = new Brand();
        brand.setId(IdUtil.simpleUUID())
                .setBrandName(brandName)
                .setCustomerId(userId);
        baseBrandMapper.insert(brand);
        newFinAccountWhenBrandRegister(brand.getId());
    }
    private void newFinAccountWhenBrandRegister(String brandId){
        FinAccount finAccount = new FinAccount();
        finAccount.setId(IdUtil.simpleUUID())
                .setFid(brandId)
                .setFidType(FidTypeEnum.BRAND.getMark())
                .setAmount(BigDecimal.ZERO)
                .setDrawable(BigDecimal.ZERO);
        finAccountMapper.insert(finAccount);
    }

}
