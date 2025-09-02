package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseBluvarrierMapper;
import com.norm.timemall.app.base.mapper.BaseBrandMapper;
import com.norm.timemall.app.base.mapper.FinAccountMapper;
import com.norm.timemall.app.base.mo.Bluvarrier;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.pojo.FetchWechatUserInfoBO;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.entity.Customer;
import com.norm.timemall.app.base.mapper.CustomerMapper;
import com.norm.timemall.app.base.entity.Account;
import com.norm.timemall.app.base.util.mate.MybatisMateEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private BaseBluvarrierMapper baseBluvarrierMapper;


    @Override
    public Account findAccountByUserName(String username) {
        LambdaQueryWrapper<Customer> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(Customer::getLoginName, mybatisMateEncryptor.defaultEncrypt(username) );
        Customer customer = customerMapper.selectOne(lambdaQuery);
        if(customer == null){
            return null;
        }
        if(CustomerMarkEnum.FREEZE.getMark().equals(customer.getMark())){
            throw new LockedException("account blocked");
        }
        Account account = new Account();
        account.setUserId(customer.getId());
        account.setName(customer.getCustomerName());
        account.setPassword(customer.getPassword());
        return account;
    }

    @Override
    public boolean doSignUpWithEmail(String username, String password) {
        // if customer already sign up return
        LambdaQueryWrapper<Customer> lambdaQuery = Wrappers.lambdaQuery();
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
        LambdaQueryWrapper<Customer> lambdaQuery = Wrappers.lambdaQuery();
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

    @Override
    public void modifyAccountMark(String mark, String brandId) {
        LambdaUpdateWrapper<Brand> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(Brand::getMark,mark);
        wrapper.set(Brand::getModifiedAt,new Date());
        wrapper.eq(Brand::getId,brandId);
        baseBrandMapper.update(null,wrapper);
    }

    @Override
    public void doSignUpWithWechatUserInfo(FetchWechatUserInfoBO wechatUserInfo) {
        // if customer already sign up return
        LambdaQueryWrapper<Customer> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(Customer::getUnionid, wechatUserInfo.getUnionid() );
        Long cnt = customerMapper.selectCount(lambdaQuery);
        if(cnt > 0){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_ALREADY_EXIST);
        }
        // register account
        String encryptedPassword= new BCryptPasswordEncoder().encode(RandomUtil.randomString(6));

        Customer customer = new Customer();
        customer.setId(IdUtil.simpleUUID())
                .setCustomerName( wechatUserInfo.getNickname())
                .setUnionid(wechatUserInfo.getUnionid())
                .setPassword(encryptedPassword)
                .setRegistAt(new Date())
                .setModifiedAt(new Date());

        customerMapper.insert(customer);

        // bind a brand for new user
        newBrandWhenWechatUserRegister(customer.getId(),wechatUserInfo);

    }

    @Override
    public Account findAccountByUnionid(String unionid) {
        LambdaQueryWrapper<Customer> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(Customer::getUnionid, unionid );
        Customer customer = customerMapper.selectOne(lambdaQuery);
        if(customer == null){
            return null;
        }
        Account account = new Account();
        account.setUserId(customer.getId());
        account.setName(customer.getCustomerName());
        account.setPassword(customer.getPassword());
        return account;
    }

    @Override
    public UserDetails loadUserForWechatLogin(String unionid) {
        Account account = findAccountByUnionid(unionid);
        if(account==null){
            return null;
        }
        String  brandId = findBrandInfoByUserId(account.getUserId()).getId();
        //定义权限列表.
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User userDetails = new CustomizeUser(account.getUserId(),account.getName(),account.getPassword(),brandId,authorities);
        return userDetails;

    }

    @Override
    public UserDetails loadUserForPhoneOrEmailLogin(String username) {
        Account account = findAccountByUserName(username);
        if(account==null){
            return null;
        }
        String  brandId = findBrandInfoByUserId(account.getUserId()).getId();
        //定义权限列表.
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User userDetails = new CustomizeUser(account.getUserId(),account.getName(),account.getPassword(),brandId,authorities);
        return userDetails;
    }

    @Override
    public void doUniSignUp(String emailOrPhone, String password) {
        boolean isMobile = Validator.isMobile(emailOrPhone);
        boolean isEmail = Validator.isEmail(emailOrPhone);
        if(!(isEmail || isMobile)){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_DISABLE);
        }
        if(isEmail){
            doSignUpWithEmail(emailOrPhone,password);
        }
        if(isMobile){
            doSignUpWithPhone(emailOrPhone,password);
        }
    }

    @Override
    public void blockedAccount(String userId) {
        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<Bluvarrier> lambdaQueryWrapper= Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Bluvarrier::getRoleCode, BluvarrierRoleEnum.HOSTING.getMark());
        Bluvarrier bluvarrier = baseBluvarrierMapper.selectOne(lambdaQueryWrapper);
        if(bluvarrier==null || !currentBrandId.equals(bluvarrier.getBrandId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        Customer targetUser = customerMapper.selectById(userId);
        if(targetUser==null){
            throw new QuickMessageException("user not exist");
        }
        Customer customer =new Customer();
        customer.setId(userId)
                .setModifiedAt(new Date())
                .setMark(CustomerMarkEnum.FREEZE.getMark());

        customerMapper.updateById(customer);

    }

    @Override
    public void topUpElectricity(String buyerBrandId, int defaultPoints) {
        Brand brand = baseBrandMapper.selectById(buyerBrandId);
        brand.setElectricity(brand.getElectricity()+defaultPoints);
        brand.setModifiedAt(new Date());
        baseBrandMapper.updateById(brand);
    }

    private void newBrandWhenUserRegister(String userId,String brandName){
        Brand brand = new Brand();
        String brandId=IdUtil.simpleUUID();
        brand.setId(brandId)
                .setElectricity(OperatorConfig.electricityGiftOnRegister)
                .setMark(BrandMarkEnum.CREATED.getMark())
                .setBrandName(brandName)
                .setCustomerId(userId)
                .setHandle("@"+brandId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        baseBrandMapper.insert(brand);
        newFinAccountWhenBrandRegister(brand.getId());
    }
    private void newBrandWhenWechatUserRegister(String userId,FetchWechatUserInfoBO wechatUserInfoBO){
        Brand brand = new Brand();
        String brandId=IdUtil.simpleUUID();
        brand.setId(brandId)
                .setElectricity(OperatorConfig.electricityGiftOnRegister)
                .setAvator(wechatUserInfoBO.getHeadimgurl())
                .setMark(BrandMarkEnum.CREATED.getMark())
                .setBrandName(wechatUserInfoBO.getNickname())
                .setCustomerId(userId)
                .setHandle("@"+brandId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
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

    public boolean doSignUpWithPhone(String emailOrPhone, String password){

       // if customer already sign up return
        LambdaQueryWrapper<Customer> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(Customer::getLoginName, mybatisMateEncryptor.defaultEncrypt(emailOrPhone) );
        Long cnt = customerMapper.selectCount(lambdaQuery);
        if(cnt > 0){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_ALREADY_EXIST);
        }
        // register account
        String encryptedPassword= new BCryptPasswordEncoder().encode(password);

        Customer customer = new Customer();
        customer.setId(IdUtil.simpleUUID())
                .setNotifyPhone(emailOrPhone)
                .setCustomerName( emailOrPhone )
                .setPassword(encryptedPassword)
                .setLoginName(emailOrPhone)
                .setRegistAt(new Date())
                .setModifiedAt(new Date());

        customerMapper.insert(customer);

       // bind a brand for new user
        newBrandWhenUserRegister(customer.getId(),emailOrPhone);
        return true;

    }

}
