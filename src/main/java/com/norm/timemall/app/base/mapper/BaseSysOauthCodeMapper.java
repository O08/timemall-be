package com.norm.timemall.app.base.mapper;

import com.norm.timemall.app.base.mo.SysOauthCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (sys_oauth_code)数据Mapper
 *
 * @author kancy
 * @since 2026-04-19 11:56:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BaseSysOauthCodeMapper extends BaseMapper<SysOauthCode> {
    @Select("SELECT user_id FROM sys_oauth_code WHERE code = #{code} AND expire_time > NOW()")
    String selectUserIdByCode(@Param("code") String code);
}
