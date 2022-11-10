package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.entity.Customer;
import com.norm.timemall.app.base.mapper.CustomerMapper;
import com.norm.timemall.app.base.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public Account findAccountByUserName(String username) {
        LambdaQueryWrapper<Customer> lambdaQuery = Wrappers.<Customer>lambdaQuery();
        lambdaQuery.eq(Customer::getLoginName, username);
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
        lambdaQuery.eq(Customer::getLoginName, username);
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

}
