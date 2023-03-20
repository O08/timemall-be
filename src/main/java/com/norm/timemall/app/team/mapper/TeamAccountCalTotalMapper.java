package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.AccountCalTotal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (account_cal_total)数据Mapper
 *
 * @author kancy
 * @since 2023-03-03 14:15:43
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAccountCalTotalMapper extends BaseMapper<AccountCalTotal> {
@Select("select * from account_cal_total where fid=#{fid} and fid_type=#{fidType}")
    AccountCalTotal selectByFIdAndType(@Param("fid") String fid,@Param("fidType") String fidType);
}
