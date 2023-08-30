package com.norm.timemall.app.ms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.ms.domain.pojo.MsFetchPrivateFriendProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * (brand)数据Mapper
 *
 * @author kancy
 * @since 2022-10-26 15:23:17
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MsBrandMapper extends BaseMapper<Brand> {
    @Select("select b.customer_id id,b.brand_name username,b.avator avatar from brand b where b.customer_id=#{friend_id}")
    MsFetchPrivateFriendProfile selectOneFriendProfile(@Param("friend_id") String friend);
}
