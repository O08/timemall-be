package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.studio.domain.dto.StudioBrandBasicInfoDTO;
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

    @Update(value = "update brand set real_name=#{real_name}, email = #{email}, phone = #{phone} where id = #{id}")
    void updateBrandContactById(@Param("id") String brandId,
                                @Param("email") String email,
                                @Param("phone") String phone,
                                @Param("real_name") String realName
    );
    @Update(value="update brand set alipay = #{uri} where id = #{id}")
    void updateAliPayById(@Param("id") String brandId,@Param("uri") String uri);
    @Update(value="update brand set wechatpay = #{uri} where id = #{id}")
    void updateWechatPayById(@Param("id")  String brandId, @Param("uri") String uri);

    @Update(value="update brand set cover = #{uri} where id = #{id}")
    void updateBrandCoverById(@Param("id") String brandId, @Param("uri") String uri);
    @Update(value="update brand set avator = #{uri} where id = #{id}")
    void updateBrandAvatar(@Param("id") String brandId, @Param("uri") String uri);
    @Update(value="update brand set wechat = #{uri} where id = #{id}")
    void updateBrandWechatQr(@Param("id") String brandId, @Param("uri") String uri);
    @Update(value="update brand set brand_name = #{dto.brand},title= #{dto.title},location=#{dto.location},handle=#{dto.handle},pd_oasis_id=#{dto.pdOasisId} where id = #{id}  and customer_id = #{user_id}")
    void updateBrandBasicInfo(@Param("id") String brandId, @Param("user_id") String userId, @Param("dto") StudioBrandBasicInfoDTO dto);
    @Update(value="update brand set skills = #{skills} where id = #{id}  and customer_id = #{user_id}")
    void updateBrandSkills(@Param("id")String brandId, @Param("user_id")String userId, @Param("skills")String skillJson);
    @Update(value="update brand set experience = #{experience} where id = #{id}  and customer_id = #{user_id}")
    void updateBrandExperience(@Param("id") String brandId, @Param("user_id") String userId, @Param("experience") String experienceJson);
    @Update(value="update brand set blue_begain_at = now(), blue_end_at=DATE_ADD(now(), INTERVAL 30 DAY) where  customer_id = #{user_id}")
    void updateBlueSignByUserId(@Param("user_id") String userId);
    @Update(value="update brand set links = #{links} where id = #{brand_id}")
    void updateBrandLinks(@Param("brand_id") String brandId, @Param("links") String json);

}
