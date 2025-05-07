package com.norm.timemall.app.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.FinAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * (account)数据Mapper
 *
 * @author kancy
 * @since 2023-03-15 11:04:49
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface FinAccountMapper extends BaseMapper<FinAccount> {
    @Select("select * from fin_account where fid=#{fid} and fid_type=#{type}")
    FinAccount selectOneByFid(@Param("fid") String fid,@Param("type") String fidType);

}
