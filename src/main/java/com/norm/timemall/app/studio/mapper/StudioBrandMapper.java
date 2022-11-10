package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (brand)数据Mapper
 *
 * @author kancy
 * @since 2022-10-26 15:23:17
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioBrandMapper extends BaseMapper<Brand> {
    @Update(value = "update brand set cardholder=#{cardholder} , cardno= #{cardno} where id = #{id} and customer_id = #{user_id}")
    void updateBrandBankById(@Param("id") String brandId,@Param("user_id") String userId,
                             @Param("cardholder") String cardholder,
                             @Param("cardno") String cardNo);

    @Update(value = "update brand set email = #{email}, phone = #{phone}, wechat = #{wechat} where id = #{id} and customer_id = #{user_id}")
    void updateBrandContactById(@Param("id") String brandId, @Param("user_id") String userId,
                                @Param("email") String email,
                                @Param("phone") String phone, @Param("wechat") String wechat);
    @Update(value="update brand set alipay = #{uri} where id = #{id}")
    void updateAliPayById(@Param("id") String brandId,@Param("uri") String uri);
    @Update(value="update brand set wechatpay = #{uri} where id = #{id}")
    void updateWechatPayById(@Param("id")  String brandId, @Param("uri") String uri);

    @Update(value="update brand set cover = #{uri} where id = #{id}")
    void updateBrandCoverById(@Param("id") String brandId, @Param("uri") String uri);
    @Update(value="update brand set avator = #{uri} where id = #{id}")
    void updateBrandAvatar(@Param("id") String brandId, @Param("uri") String uri);
}
