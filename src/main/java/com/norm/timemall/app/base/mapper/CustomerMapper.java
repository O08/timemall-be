package com.norm.timemall.app.base.mapper;

import com.norm.timemall.app.base.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (customer)数据Mapper
 *
 * @author kancy
 * @since 2022-10-24 19:38:28
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
@Update("update customer set password=#{u.password} where login_name=#{u.loginName}")
    void updatePasswordByUserName(@Param("u") Customer customer);
}
