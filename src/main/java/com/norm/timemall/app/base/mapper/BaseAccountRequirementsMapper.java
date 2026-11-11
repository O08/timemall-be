package com.norm.timemall.app.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.DelAccountRequirements;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * (del_account_requirements)数据Mapper
 *
 * @author kancy
 * @since 2023-10-05 15:07:33
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BaseAccountRequirementsMapper extends BaseMapper<DelAccountRequirements> {


    void callGenRequirementFunId(@Param("user_id") String userId,@Param("brand_id") String brandId);
}
