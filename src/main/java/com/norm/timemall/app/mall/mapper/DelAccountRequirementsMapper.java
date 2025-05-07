package com.norm.timemall.app.mall.mapper;

import com.norm.timemall.app.base.mo.DelAccountRequirements;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.mall.domain.ro.FetchDelAccountRequirementRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (del_account_requirements)数据Mapper
 *
 * @author kancy
 * @since 2023-10-05 15:07:33
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface DelAccountRequirementsMapper extends BaseMapper<DelAccountRequirements> {
@Select("select item_code,item,target_val,current_val,land_url,create_at from del_account_requirements where user_id=#{user_id}")
    ArrayList<FetchDelAccountRequirementRO> selectRequirementsByUserId(@Param("user_id") String userId);

    void callGenRequirementFunId(@Param("user_id") String userId,@Param("brand_id") String brandId);
}
