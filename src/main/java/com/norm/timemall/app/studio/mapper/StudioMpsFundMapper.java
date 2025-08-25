package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.MpsFund;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsFund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (mps_fund)数据Mapper
 *
 * @author kancy
 * @since 2023-06-14 16:24:16
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioMpsFundMapper extends BaseMapper<MpsFund> {

    @Select("select f.id,f.balance mpsBalance,a.drawable from mps_fund f left join fin_account a on f.founder=a.fid and a.fid_type=#{fidType} where f.founder=#{brandId}")
    StudioFetchMpsFund selectMpsFundByBrandId(@Param("brandId") String brandId,@Param("fidType") String fidType);
}
