package com.norm.timemall.app.mall.mapper;

import com.norm.timemall.app.base.mo.CustomerInterests;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.mall.domain.ro.FetchUserInterestsRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (customer_interests)数据Mapper
 *
 * @author kancy
 * @since 2023-10-05 15:07:33
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface CustomerInterestsMapper extends BaseMapper<CustomerInterests> {
@Select("select item_code,item,land_url,val,create_at from customer_interests where user_id=#{user_id}")
    ArrayList<FetchUserInterestsRO> selectInterestListByUserId(@Param("user_id") String userId);

    void callGenInterestIndFunById(@Param("user_id") String userId);

}
