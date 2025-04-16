package com.norm.timemall.app.team.mapper;

import cn.hutool.core.date.DateTime;
import com.norm.timemall.app.base.mo.WithdrawRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * (withdraw_record)数据Mapper
 *
 * @author kancy
 * @since 2023-03-16 11:36:48
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamWithdrawRecordMapper extends BaseMapper<WithdrawRecord> {
@Select("select ifnull(sum(r.amount),0) from withdraw_record r where r.brand_id=#{brandId} and r.create_at >= #{beginOfDay}")
    BigDecimal selectUsedCreditIn24Hour(@Param("brandId") String brandId, @Param("beginOfDay") DateTime beginOfDay);
}
