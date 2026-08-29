package com.norm.timemall.app.base.mapper;

import com.norm.timemall.app.base.mo.UserPersonalToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.pojo.ro.PatListRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * (user_personal_token)数据Mapper
 *
 * @author kancy
 * @since 2026-08-13 14:17:27
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BaseUserPersonalTokenMapper extends BaseMapper<UserPersonalToken> {

    @Select("select * from user_personal_token where token_hash = #{tokenHash}")
    Optional<UserPersonalToken> findByTokenHash(@Param("tokenHash") String tokenHash);

    @Update("UPDATE user_personal_token p SET p.last_used_at = #{now} WHERE p.token_hash = #{tokenHash}")
    void updateLastUsedAt(@Param("tokenHash") String tokenHash, @Param("now") LocalDateTime now);
    @Select("select * from user_personal_token where brand_id = #{currentBrandId} order by created_at desc")
    List<UserPersonalToken> findByBrandId(@Param("currentBrandId") String currentBrandId);

}
